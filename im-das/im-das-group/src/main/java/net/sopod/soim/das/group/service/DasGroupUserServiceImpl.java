package net.sopod.soim.das.group.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.group.api.model.dto.GroupUser_0;
import net.sopod.soim.das.group.api.model.entity.ImGroupUser;
import net.sopod.soim.das.group.api.service.DasGroupUserService;
import net.sopod.soim.das.group.dao.ImGroupUserMapper;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DasGroupUserServiceImpl
 *
 * @author tmy
 * @date 2022-06-03 20:30
 */
@DubboService
public class DasGroupUserServiceImpl implements DasGroupUserService {

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public Boolean isJoined(Long groupId, Long uid) {
        LambdaQueryWrapper<ImGroupUser> joinIdQuery = new QueryWrapper<ImGroupUser>().lambda()
                .select(ImGroupUser::getId)
                .eq(ImGroupUser::getGroupId, groupId)
                .eq(ImGroupUser::getUid, uid);
        return Collects.isNotEmpty(imGroupUserMapper.selectList(joinIdQuery));
    }

    @Override
    public Long insert(Long groupId, Long uid) {
        long id = segmentIdGenerator.nextId(LogicTables.IM_GROUP_USER);
        Date now = ImClock.date();
        ImGroupUser imGroupUser = new ImGroupUser().setId(id)
                .setUid(uid)
                .setGroupId(groupId)
                .setCreateTime(now)
                .setLastActive(now)
                .setUnreadNum(0)
                .setUnreadOffsetId(0L);
        imGroupUserMapper.insert(imGroupUser);
        return id;
    }

    @Override
    public void updateLastActive(Long id, Long millis) {
        ImGroupUser updateWrapper = new ImGroupUser()
                .setId(id)
                .setLastActive(new Date(millis));
        imGroupUserMapper.updateById(updateWrapper);
    }

    @Override
    public List<GroupUser_0> listGroupUsers(Long groupId) {
        LambdaQueryWrapper<ImGroupUser> imGroupUserQueryWrapper = new QueryWrapper<ImGroupUser>().lambda()
                .select(ImGroupUser::getUid, ImGroupUser::getLastActive)
                .eq(ImGroupUser::getGroupId, groupId);
        List<ImGroupUser> imGroupUsers = imGroupUserMapper.selectList(imGroupUserQueryWrapper);
        List<GroupUser_0> groupUsers = imGroupUsers.stream().map(gu -> new GroupUser_0()
                .setUid(gu.getUid())
                .setLastActive(gu.getLastActive() == null ? null : gu.getLastActive().getTime())
        ).collect(Collectors.toList());
        return groupUsers;
    }

}
