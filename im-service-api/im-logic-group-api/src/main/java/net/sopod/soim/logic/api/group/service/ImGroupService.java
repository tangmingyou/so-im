package net.sopod.soim.logic.api.group.service;

import net.sopod.soim.das.group.api.model.dto.GroupView;
import net.sopod.soim.das.group.api.model.entity.ImGroup;
import net.sopod.soim.logic.api.group.model.dto.GroupUserInfo;

import java.util.List;

/**
 * ImGroupService
 *
 * @author tmy
 * @date 2022-06-03 20:54
 */
public interface ImGroupService {

    Long createGroup(Long uid, String groupName);

    List<ImGroup> searchGroup(String groupNameLike);

    Long joinGroup(Long groupId, Long uid);

    List<GroupView> listUserGroups(Long uid);

    List<GroupUserInfo> listGroupUsers(Long groupId);

}
