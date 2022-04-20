package net.sopod.soim.logic.user.service;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.common.util.TokenUtil;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDasService;
import net.sopod.soim.logic.user.auth.model.ImAuth;
import net.sopod.soim.logic.user.auth.service.UserAuthService;
import net.sopod.soim.logic.user.config.AuthConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private AuthConfig authConfig;

    @Override
    public ImAuth pwdAuth(String account, String password) {
        ImUser imUser = userDasService.getNormalUserByAccount(account);
        if (imUser == null) {
            return new ImAuth().setSuccess(false).setMessage("账号不存在");
        }
        if (StringUtils.isEmpty(imUser.getPassword())
                || !Objects.equals(imUser.getPassword(), password)) {
            return new ImAuth().setSuccess(false).setMessage("密码有误");
        }
        String token = TokenUtil.genToken(imUser.getId());
        return new ImAuth()
                .setSuccess(true)
                .setAuthToken(token)
                .setExpireMs(ImClock.millis()
                        + TimeUnit.SECONDS.toMillis(authConfig.getAuthTokenExpire()));
    }

}
