package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsLogin;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.config.ClientConfig;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.model.dto.LoginResDTO;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.client.util.HttpClient;
import net.sopod.soim.data.msg.auth.Auth;

import java.util.Collections;
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
        Console.info("登录中...");
        LoginResDTO loginRes = HttpClient.restPost(clientConfig.getEntryHttpHost() + "/auth/pwdAuth", params, LoginResDTO.class);
        if (!Boolean.TRUE.equals(loginRes.getSuccess())) {
            Console.error("登录失败: {}", loginRes.getMessage());
            return;
        }
        Auth.ReqTokenAuth reqTokenAuth = Auth.ReqTokenAuth.newBuilder()
                .setUid(loginRes.getUid())
                .setToken(loginRes.getAuthToken())
                .build();
        // 获取 im-entry host
        String entryHost = HttpClient.restGet(clientConfig.getEntryHttpHost() + "/monitor/entryHost", String.class);
        Console.info("登录成功: uid={}, entryHost={}", loginRes.getUid(), entryHost);
        String[] hostPort = entryHost.split(":");
        soImSession.connect(hostPort[0], Integer.parseInt(hostPort[1]), reqTokenAuth, args.getAccount());
    }

}
