package net.sopod.soim.logic.user.service;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.TokenUtil;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.user.auth.model.ImAuth;
import net.sopod.soim.logic.api.user.auth.service.UserAuthService;
import net.sopod.soim.logic.user.config.AuthConfig;
import net.sopod.soim.router.api.model.RegistryRes;
import net.sopod.soim.router.api.service.UserRouteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
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
    private UserDas userDas;

    @DubboReference
    private UserRouteService userRouteService;

    @Resource
    private AuthConfig authConfig;

    @Override
    public ImAuth pwdAuth(String account, String password) {
        ImUser imUser = userDas.getNormalUserByAccount(account);
        ImAuth imAuth = new ImAuth()
                .setSuccess(false)
                .setExpireMs(-1L)
                .setUid(-1L);
        if (imUser == null) {
            return imAuth.setMessage("账号不存在");
        }
        if (StringUtils.isEmpty(imUser.getPassword())
                || !Objects.equals(imUser.getPassword(), password)) {
            return imAuth.setMessage("密码有误");
        }
        String token = TokenUtil.genToken(imUser.getId());
        return new ImAuth()
                .setSuccess(true)
                .setAuthToken(token)
                .setUid(imUser.getId())
                .setExpireMs(ImClock.millis()
                        + TimeUnit.SECONDS.toMillis(authConfig.getAuthTokenExpire()));
    }

    @Override
    public String validateToken(String token, String imEntryAddr) {
        TokenUtil.Payload payload = TokenUtil.validateAndParse(token);
        if (payload == null) {
            return null;
        }
        // 注册记录用户登录的 entry 节点
        RpcContext.getServiceContext()
                .setAttachment(DubboConstant.CTX_UID, String.valueOf(payload.getUserId()));
        RegistryRes registryRes = userRouteService
                .registryUserEntry(payload.getUserId(), imEntryAddr);

        return !Boolean.TRUE.equals(registryRes.getSuccess()) ? null : registryRes.getImRouterId();
    }

}
