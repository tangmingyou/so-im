package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsLogin;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.config.ClientConfig;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.model.dto.LoginResDTO;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.client.util.HttpClient;
import net.sopod.soim.data.msg.auth.Auth;

import java.util.HashMap;

/**
 * LoginCmdHandler
 *
 * @author tmy
 * @date 2022-04-25 10:53
 */
@Singleton
public class LoginHandler implements CmdHandler<ArgsLogin> {

    @Inject
    private ClientConfig clientConfig;

    @Inject
    private SoImSession soImSession;

    @Override
    public ArgsLogin newArgsInstance() {
        return new ArgsLogin();
    }

    @Override
    public void handleArgs(ArgsLogin args) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account", args.getAccount());
        params.put("password", args.getPassword());
        Logger.info("登录中...");
        LoginResDTO loginRes = HttpClient.restPost(clientConfig.getLoginUrl(), params, LoginResDTO.class);
        if (!Boolean.TRUE.equals(loginRes.getSuccess())) {
            Logger.error("登录失败: {}", loginRes.getMessage());
            return;
        }
        Logger.info("登录成功: {}", loginRes.getUid());

        Auth.ReqTokenAuth reqTokenAuth = Auth.ReqTokenAuth.newBuilder()
                .setUid(loginRes.getUid())
                .setToken(loginRes.getAuthToken())
                .build();
        soImSession.connect(clientConfig.getHost(), clientConfig.getPort(), reqTokenAuth);
    }

}
