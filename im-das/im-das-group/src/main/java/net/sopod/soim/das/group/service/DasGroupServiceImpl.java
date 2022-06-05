package net.sopod.soim.das.group.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.group.api.model.entity.ImGroup;
import net.sopod.soim.das.group.api.service.DasGroupService;
import net.sopod.soim.das.group.dao.ImGroupMapper;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public List<ImGroup> listGroup(String groupNameLike) {
        LambdaQueryWrapper<ImGroup> imGroupQuery = new QueryWrapper<ImGroup>().lambda()
                .likeRight(ImGroup::getGroupName, groupNameLike);
        return imGroupMapper.selectList(imGroupQuery);
    }

}
