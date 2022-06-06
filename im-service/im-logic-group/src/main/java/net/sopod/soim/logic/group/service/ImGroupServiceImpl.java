package net.sopod.soim.logic.group.service;

import net.sopod.soim.common.dubbo.exception.LogicException;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.group.api.model.dto.GroupUser_0;
import net.sopod.soim.das.group.api.model.dto.GroupView;
import net.sopod.soim.das.group.api.model.entity.ImGroup;
import net.sopod.soim.das.group.api.service.DasGroupService;
import net.sopod.soim.das.group.api.service.DasGroupUserService;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.group.model.dto.GroupUserInfo;
import net.sopod.soim.logic.api.group.service.ImGroupService;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import net.sopod.soim.router.api.service.UserRouteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ImGroupService
 *
 * @author tmy
 * @date 2022-06-03 21:06
 */
@DubboService
public class ImGroupServiceImpl implements ImGroupService {

    @DubboReference
    private DasGroupService dasGroupService;

    @DubboReference
    private DasGroupUserService dasGroupUserService;

    @DubboReference
    private UserDas userDas;

    @DubboReference
    private UserRouteService userRouteService;

    @Override
    public Long createGroup(Long uid, String groupName) {
        Boolean notExists = dasGroupService.isGroupNameNotExists(groupName);
        if (!Boolean.TRUE.equals(notExists)) {
            throw new LogicException("群名称已存在");
        }
        ImGroup imGroup = new ImGroup()
                .setGroupName(groupName)
                .setMasterUid(uid)
                .setUserLimit(0);
        return dasGroupService.saveGroup(imGroup);
    }

    @Override
    public List<ImGroup> searchGroup(String groupNameLike) {
        return dasGroupService.searchGroup(groupNameLike);
    }

    @Override
    public Long joinGroup(Long groupId, Long uid) {
        Boolean isJoined = dasGroupUserService.isJoined(groupId, uid);
        if (isJoined) {
            throw new LogicException("你已加入该群聊");
        }
        return dasGroupUserService.insert(groupId, uid);
    }

    @Override
    public List<GroupView> listUserGroups(Long uid) {
        return dasGroupService.listUserGroup(uid);
    }

    @Override
    public List<GroupUserInfo> listGroupUsers(Long groupId) {
        List<GroupUser_0> groupUsers = dasGroupUserService.listGroupUsers(groupId);
        Map<Long, GroupUser_0> groupUserMap = Collects.collect2Map(
                groupUsers,
                GroupUser_0::getUid,
                new HashMap<>(Collects.mapCapacity(groupUsers.size()))
        );

        List<Long> userIds = groupUsers.stream()
                .map(GroupUser_0::getUid)
                .collect(Collectors.toList());
        // 查询用户信息
        List<ImUser> imUsers = userDas.listUsers(userIds);
        // 分组到 im-router 查询在线状态
        UidConsistentHashSelector<?> selector = UidConsistentHashSelector.getCurrent();
        // 单实例 im-router，TODO 规则整合到负载均衡
        if (selector == null) {
            List<Boolean> onlineUsers = userRouteService.isOnlineUsers(userIds);
            this.mappingOnlineState(userIds, onlineUsers, groupUserMap);
        } else {
            // 将一致性hash路由到不同router的uid放到不同集合
            Map<?, List<Long>> userRouteGroup =
                    Collects.group(userIds, uid -> selector.select(StringUtil.toString(uid)), uid -> uid);
            for (List<Long> routeUidGroup : userRouteGroup.values()) {
                RpcContextUtil.setContextUid(routeUidGroup.get(0));
                // 调用在线状态查询接口，按返回 list 索引更新在线状态
                List<Boolean> onlineUsers = userRouteService.isOnlineUsers(routeUidGroup);
                this.mappingOnlineState(routeUidGroup, onlineUsers, groupUserMap);
            }
        }
        return imUsers.stream().map(u -> {
                    GroupUser_0 groupUser = groupUserMap.get(u.getId());
                    return new GroupUserInfo()
                            .setUid(u.getId())
                            .setAccount(u.getAccount())
                            .setNickname(u.getNickname())
                            .setOnline(Boolean.TRUE.equals(groupUser.getIsOnline()))
                            .setLastActive(groupUser.getLastActive());
                }
        ).collect(Collectors.toList());
    }

    /**
     * 整合在线状态结果
     */
    private void mappingOnlineState(List<Long> userIds,
                      List<Boolean> userOnlines,
                      Map<Long, GroupUser_0> groupUserMap) {
        for (int i = 0; i < userIds.size(); i++) {
            GroupUser_0 groupUser = groupUserMap.get(userIds.get(i));
            if (groupUser != null) {
                groupUser.setIsOnline(Boolean.TRUE.equals(userOnlines.get(i)));
            }
        }
    }

}
