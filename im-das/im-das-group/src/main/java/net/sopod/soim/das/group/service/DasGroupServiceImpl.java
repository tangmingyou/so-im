package net.sopod.soim.das.group.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.group.api.model.dto.GroupView;
import net.sopod.soim.das.group.api.model.entity.ImGroup;
import net.sopod.soim.das.group.api.model.entity.ImGroupUser;
import net.sopod.soim.das.group.api.service.DasGroupService;
import net.sopod.soim.das.group.dao.ImGroupMapper;
import net.sopod.soim.das.group.dao.ImGroupUserMapper;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DasGroupServiceImpl
 *
 * @author tmy
 * @date 2022-06-03 20:29
 */
@DubboService
public class DasGroupServiceImpl implements DasGroupService {

    @Resource
    private ImGroupMapper imGroupMapper;

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    @Resource
    private DasGroupUserServiceImpl dasGroupUserService;

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public Boolean isGroupNameNotExists(String groupName) {
        Long groupId = imGroupMapper.selectExistsGroupByName(groupName);
        return groupId == null;
    }

    @Transactional
    @Override
    public Long saveGroup(ImGroup imGroup) {
        long groupId = segmentIdGenerator.nextId(LogicTables.IM_GROUP);
        // 添加群数据
        imGroup.setId(groupId)
                .setStatus(LogicTables.STATUS_NORMAL)
                .setUserNum(1)
                .setCreateTime(ImClock.date());
        imGroupMapper.insert(imGroup);
        // 添加群主为群用户
        dasGroupUserService.insert(groupId, imGroup.getMasterUid());
        return groupId;
    }

    @Override
    public List<ImGroup> searchGroup(String groupNameLike) {
        LambdaQueryWrapper<ImGroup> imGroupQuery = new QueryWrapper<ImGroup>().lambda()
                .likeRight(ImGroup::getGroupName, groupNameLike);
        return imGroupMapper.selectList(imGroupQuery);
    }

    @Override
    public List<GroupView> listUserGroup(Long uid) {
        LambdaQueryWrapper<ImGroupUser> groupUsersWrapper = new QueryWrapper<ImGroupUser>().lambda()
                .select(ImGroupUser::getGroupId, ImGroupUser::getUnreadNum)
                .eq(ImGroupUser::getUid, uid);
        List<ImGroupUser> imGroupUsers = imGroupUserMapper.selectList(groupUsersWrapper);
        if (Collects.isEmpty(imGroupUsers)) {
            return Collections.emptyList();
        }
        // 准备数据
        List<Long> groupIds = imGroupUsers.stream().map(ImGroupUser::getGroupId).collect(Collectors.toList());
        Map<Long, ImGroupUser> guMap = Collects.collect2Map(imGroupUsers, ImGroupUser::getGroupId);
        // 查询群信息
        LambdaQueryWrapper<ImGroup> imGroupWrapper = new QueryWrapper<ImGroup>().lambda()
                .select(ImGroup::getId,
                        ImGroup::getGroupName,
                        ImGroup::getMasterUid,
                        ImGroup::getUserNum,
                        ImGroup::getUserLimit)
                .in(ImGroup::getId, groupIds)
                .eq(ImGroup::getStatus, LogicTables.STATUS_NORMAL);
        // 查询群信息
        List<ImGroup> imGroups = imGroupMapper.selectList(imGroupWrapper);
        // 映射结果
        return imGroups.stream().map(g -> {
            ImGroupUser gu = guMap.get(g.getId());
            return new GroupView()
                    .setGid(g.getId())
                    .setGName(g.getGroupName())
                    .setMasterUid(g.getMasterUid())
                    .setUserNum(g.getUserNum())
                    .setCreateTime(g.getCreateTime())
                    .setUserLimit(g.getUserLimit())
                    .setUnreadNum(gu.getUnreadNum());
        }).collect(Collectors.toList());
    }

}
