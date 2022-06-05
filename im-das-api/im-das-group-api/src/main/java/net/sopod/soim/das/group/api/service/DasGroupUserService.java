package net.sopod.soim.das.group.api.service;

import net.sopod.soim.das.group.api.model.dto.GroupUser_0;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * DasGroupUserService
 *
 * @author tmy
 * @date 2022-06-03 20:22
 */
public interface DasGroupUserService {

    Boolean isJoined(Long groupId, Long uid);

    Long insert(Long groupId, Long uid);

    void updateLastActive(Long id, Long millis);

    List<GroupUser_0> listGroupUsers(Long groupId);

}
