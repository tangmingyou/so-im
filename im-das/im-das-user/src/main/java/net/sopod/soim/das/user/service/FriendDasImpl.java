package net.sopod.soim.das.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import net.sopod.soim.common.constant.LogicConsts;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.user.api.config.LogicTables;
import net.sopod.soim.das.user.api.model.entity.ImFriend;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
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

    SegmentIdGenerator segmentIdGenerator;

    private FriendMapper friendMapper;

    private UserMapper userMapper;


    @Override
    public int insert(Long uid, Long fid) {
        long id = segmentIdGenerator.nextId(LogicTables.IM_FRIEND);
        ImFriend imFriend = new ImFriend()
                .setId(id)
                .setUid(uid)
                .setFid(fid)
                .setStatus(1)
                .setCreateTime(ImClock.date());
        return friendMapper.insert(imFriend);
    }

    @Override
    public Boolean isExists(Long uid, Long fid) {
        LambdaQueryWrapper<ImFriend> friendQuery = new QueryWrapper<ImFriend>().lambda()
                .eq(ImFriend::getUid, uid)
                .eq(ImFriend::getFid, fid)
                .eq(ImFriend::getStatus, 1);
        List<ImFriend> imFriends = friendMapper.selectList(friendQuery);
        if (Collects.isEmpty(imFriends)) {
            return Boolean.FALSE;
        }
        if (imFriends.size() > 1) {
            logger.warn("重复的好友数据: user={}, friend={}", uid, fid);
        }
        return Boolean.TRUE;
    }

    @Override
    public int remove(Long uid, Long fid) {
        LambdaQueryWrapper<ImFriend> friendQuery = new QueryWrapper<ImFriend>().lambda()
                .eq(ImFriend::getUid, uid)
                .eq(ImFriend::getFid, fid)
                .eq(ImFriend::getStatus, 1);
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
                .eq(ImUser::getStatus, LogicConsts.STATUS_NORMAL);
        return userMapper.selectList(userQuery);
    }

}
