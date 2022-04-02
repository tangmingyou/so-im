package net.sopod.soim.logic.segmentid.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.ObjectUtil;
import net.sopod.soim.logic.segmentid.api.model.dto.SegmentDTO;
import net.sopod.soim.logic.segmentid.api.service.SegmentIdService;
import net.sopod.soim.logic.segmentid.config.SegmentConfig;
import net.sopod.soim.logic.segmentid.mapper.SegmentIdMapper;
import net.sopod.soim.logic.segmentid.model.entity.SegmentId;
import net.sopod.soim.logic.segmentid.util.RedisLockUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ThreadLocalRandom;

/**
 * SegmentIdService
 *
 * @author tmy
 * @date 2022-04-02 15:22
 */
@DubboService
@AllArgsConstructor
public class SegmentIdServiceImpl implements SegmentIdService {

    private static final Logger logger = LoggerFactory.getLogger(SegmentIdServiceImpl.class);

    private final SegmentIdMapper segmentIdMapper;

    private final RedisTemplate<String, String> redisTemplate;

    private final SegmentConfig segmentConfig;

    @Override
    public SegmentDTO nextSegmentId(String bizTag) {
        return nextSegmentId(bizTag, null);
    }

    @Override
    public SegmentDTO nextSegmentId(String bizTag, Long step) {
        SegmentId segmentId = segmentIdMapper.selectById(bizTag);
        if (segmentId == null) {
            String key = SegmentConfig.KEY_PREFIX_SEGMENT_ID_INSERT + bizTag;
            String val = String.valueOf(ThreadLocalRandom.current().nextInt());
            // 锁二十秒，创建数据
            boolean locked = RedisLockUtil.acquireLock(redisTemplate, key, val, 20000L);
            if (locked) {
                try {
                    // 插入分段id数据
                    SegmentId newSegmentId = new SegmentId()
                            .setBizTag(bizTag)
                            .setCreateTime(ImClock.date())
                            .setCurrentId(segmentConfig.getInitId())
                            .setVersion(0L)
                            .setInitStep(ObjectUtil.defaultValue(step, segmentConfig.getInitStep()));
                    segmentIdMapper.insert(newSegmentId);
                    segmentId = newSegmentId;
                } finally {
                    // 释放锁
                    RedisLockUtil.releaseLock(redisTemplate, key, val);
                }
            } else {
                logger.info("wait lock...");
                // 等待锁释放
                long timeout = 2000L;
                boolean released = waitReleaseRedisLock(key, timeout);
                if (!released) {
                    logger.warn("{} 插入数据超过 {} 未释放", bizTag, timeout);
                }
                segmentId = segmentIdMapper.selectById(bizTag);
            }
            if (segmentId == null) {
                throw new RuntimeException(String.format("%s分段数据不存在,新增失败", bizTag));
            }
        }
        long begin = ImClock.millis();
        for (int i = 0; ; i++) {
            if (i > 0) {
                segmentId = segmentIdMapper.selectById(bizTag);
            }
            SegmentDTO segment = getSegment(segmentId, step);
            if (segment != null) {
                if (i > 0)
                    logger.info("{} get segment try {} times", bizTag, i);
                return segment;
            }
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50) + 50L);
            } catch (InterruptedException e) {
                logger.error("wait db interrupted!", e);
            }
            long current = ImClock.millis();
            if (current - begin > segmentConfig.getGetSegmentPollDBTimeout()) {
                break;
            }
        }
        throw new RuntimeException("id分段获取失败");
    }

    private boolean waitReleaseRedisLock(String key, long timeout) {
        long begin = ImClock.millis();
        // while 阻塞等待
        while (true) {
            long current = ImClock.millis();
            if (!RedisLockUtil.hasKey(redisTemplate, key)) {
                return true;
            }
            if (current - begin > timeout) {
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("wait release redis lock interrupted!", e);
            }
        }
    }

    /**
     * 获取id段
     *
     * @param dbSegmentId 当前DB segmentId
     * @param step        步长
     */
    private SegmentDTO getSegment(SegmentId dbSegmentId, Long step) {
        step = ObjectUtil.defaultValue(step, dbSegmentId.getInitStep(), dbSegmentId.getInitStep());
        Preconditions.checkState(step > 0, "步长需大于0");
        // 开始id, 结束id
        Long currentId = dbSegmentId.getCurrentId();
        long endId = dbSegmentId.getCurrentId() + step;
        // 版本号更新条件
        LambdaQueryWrapper<SegmentId> segmentIdUpdate = new QueryWrapper<SegmentId>().lambda()
                .eq(SegmentId::getBizTag, dbSegmentId.getBizTag())
                .eq(SegmentId::getVersion, dbSegmentId.getVersion());
        SegmentId newSegment = new SegmentId()
                .setBizTag(dbSegmentId.getBizTag())
                .setUpdateTime(ImClock.date())
                .setCurrentId(endId + 1)
                .setVersion(dbSegmentId.getVersion() + 1);
        // 根据版本号条件尝试更新
        int row = segmentIdMapper.update(newSegment, segmentIdUpdate);
        if (row == 0) {
            // 未更新成功，被其他连接并发更新
            return null;
        }
        // 更新成功
        return new SegmentDTO()
                .setBeginId(currentId)
                .setEndId(endId);
    }

}
