package net.sopod.soim.logic.user.auth.service;

import net.sopod.soim.logic.user.auth.model.ImAuth;

/**
 * AuthService
 *
 * @author tmy
 * @date 2022-03-31 21:03
 */
public interface AuthService {

    /**
     * 登录请求
     */
    void login(ImAuth imAuth);

}
