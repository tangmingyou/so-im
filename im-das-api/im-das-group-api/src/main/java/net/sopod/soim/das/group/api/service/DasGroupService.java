package net.sopod.soim.das.group.api.service;

import net.sopod.soim.das.group.api.model.dto.GroupView;
import net.sopod.soim.das.group.api.model.entity.ImGroup;

import java.util.List;

/**
 * DasGroupService
 *
 * @author tmy
 * @date 2022-06-03 20:04
 */
public interface DasGroupService {

    Boolean isGroupNameNotExists(String groupName);

    Long saveGroup(ImGroup imGroup);

    List<ImGroup> searchGroup(String groupNameLike);

    List<GroupView> listUserGroup(Long uid);

}
