package net.sopod.soim.das.user.api.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;

import java.util.List;

/**
 * DasUserService
 *
 * @author tmy
 * @date 2022-04-13 23:28
 */
public interface UserDas {

    ImUser getUserById(Long id);

    ImUser getNormalUserByAccount(String account);

    List<ImUser> searchImUser(String keyword, Integer limit);

}
