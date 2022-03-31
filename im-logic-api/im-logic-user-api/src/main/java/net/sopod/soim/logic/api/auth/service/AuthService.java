package net.sopod.soim.logic.api.auth.service;

import net.sopod.soim.logic.api.auth.model.ReqLogin;

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
    void login(ReqLogin reqLogin);

}
