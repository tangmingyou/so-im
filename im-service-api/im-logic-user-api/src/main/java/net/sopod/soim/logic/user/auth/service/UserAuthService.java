package net.sopod.soim.logic.user.auth.service;

import net.sopod.soim.logic.user.auth.model.ImAuth;

/**
 * AuthService
 *
 * @author tmy
 * @date 2022-04-13 22:55
 */
public interface UserAuthService {

    ImAuth pwdAuth(String account, String password);

}
