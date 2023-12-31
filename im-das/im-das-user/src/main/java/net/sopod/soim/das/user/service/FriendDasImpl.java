package net.sopod.soim.das.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.user.api.model.entity.ImFriend;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
import net.sopod.soim.das.user.cache.FriendRelationCache;
import net.sopod.soim.das.user.dao.FriendMapper;
import net.sopod.soim.das.user.dao.UserMapper;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * FriendDasImpl
 *
 * @author tmy
 * @date 2022-05-23 16:07
 */
@DubboService
@AllArgsConstructor
public class FriendDasImpl implements FriendDas {

    private static Logger logger = LoggerFactory.getLogger(FriendDasImpl.class);

    private SegmentIdGenerator segmentIdGenerator;

    private FriendMapper friendMapper;

    private UserMapper userMapper;

    private FriendRelationCache friendRelationCache;

    @Override
    public int saveFriendRelation(Long uid, Long fid) {
        long id = segmentIdGenerator.nextId(LogicTables.IM_FRIEND);
        long relationId = segmentIdGenerator.nextId(LogicTables.IM_FRIEND_RELATION);
        ImFriend imFriend = new ImFriend()
                .setId(id)
                .setRelationId(relationId)
                .setUid(uid)
                .setFid(fid)
                .setStatus(1)
                .setUnreadNum(0)
                .setUnreadOffsetId(0L)
                .setCreateTime(ImClock.date());
        // 相互添加好友
        ImFriend imFriend2 = new ImFriend()
                .setId(id)
                .setRelationId(relationId)
                .setUid(fid)
                .setFid(uid)
                .setStatus(1)
                .setUnreadNum(0)
                .setUnreadOffsetId(0L)
                .setCreateTime(ImClock.date());
        friendMapper.insert(imFriend);
        return friendMapper.insert(imFriend2);
    }

    /**
     * TODO cache
     */
    @Override
    public Long getRelationId(Long uid, Long fid) {
        return friendRelationCache.computeIfAbsent(uid, fid, (userId, friendId) -> {
            LambdaQueryWrapper<ImFriend> friendQuery = new QueryWrapper<ImFriend>().lambda()
                    .select(ImFriend::getId, ImFriend::getRelationId)
                    .eq(ImFriend::getUid, userId)
                    .eq(ImFriend::getFid, friendId)
                    .eq(ImFriend::getStatus, LogicTables.STATUS_NORMAL);
            List<ImFriend> imFriends = friendMapper.selectList(friendQuery);
            if (Collects.isEmpty(imFriends)) {
                return null;
            }
            if (imFriends.size() > 1) {
                logger.warn("重复的好友数据: user={}, friend={}", userId, friendId);
            }
            return imFriends.get(0).getRelationId();
        });
    }

    @Override
    public Boolean isExists(Long uid, Long fid) {
        return getRelationId(uid, fid) != null;
    }

    @Override
    public int remove(Long uid, Long fid) {
        LambdaQueryWrapper<ImFriend> friendQuery = new QueryWrapper<ImFriend>().lambda()
                .eq(ImFriend::getUid, uid)
                .eq(ImFriend::getFid, fid)
                .eq(ImFriend::getStatus, LogicTables.STATUS_NORMAL);
        return friendMapper.update(new ImFriend().setStatus(0), friendQuery);
    }

    @Override
    public List<ImUser> queryAllFriend(Long uid) {
        List<Long> fids = friendMapper.selectAllFriendId(uid);
        if (Collects.isEmpty(fids)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ImUser> userQuery = new QueryWrapper<ImUser>().lambda()
                .in(ImUser::getId, fids)
                .eq(ImUser::getStatus, LogicTables.STATUS_NORMAL);
        return userMapper.selectList(userQuery);
    }

}
