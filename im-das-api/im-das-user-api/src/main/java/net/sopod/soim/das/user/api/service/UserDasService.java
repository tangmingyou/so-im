package net.sopod.soim.das.user.api.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;

/**
 * DasUserService
 *
 * @author tmy
 * @date 2022-04-13 23:28
 */
public interface UserDasService {

    ImUser getUserById(Long id);

    ImUser getNormalUserByAccount(String account);

}
