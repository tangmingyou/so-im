package net.sopod.soim.logic.user.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDasService;
import net.sopod.soim.logic.user.auth.model.ImAuth;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

/**
 * AuthServiceImpl
 *
 * @author tmy
 * @date 2022-04-13 22:57
 */
@DubboService
public class UserAuthServiceImpl implements UserAuthService {

    @DubboReference
    private UserDasService userDasService;

    @Override
    public ImAuth pwdAuth(String account, String password) {
        ImUser imUser = userDasService.getUserByAccount(account);
        if (imUser == null) {
            return null;
        }
        // TODO status,
        return null;
    }

}
