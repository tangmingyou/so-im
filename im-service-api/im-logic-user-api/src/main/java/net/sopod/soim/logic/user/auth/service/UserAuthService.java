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

    /**
     * 校验token
     * @param token 登录 token
     * @param imEntryAddr entry 节点地址，检验通过后存储到 im-router
     * @return 是否校验成功
     */
    String validateToken(String token, String imEntryAddr);

}
