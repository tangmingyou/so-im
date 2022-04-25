package net.sopod.soim.client.handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.ArgsLogin;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.config.ClientConfig;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.model.dto.LoginResDTO;
import net.sopod.soim.client.util.HttpClient;

import java.util.HashMap;

/**
 * LoginCmdHandler
 *
 * @author tmy
 * @date 2022-04-25 10:53
 */
@Singleton
public class LoginCmdHandler implements CmdHandler<ArgsLogin> {

    @Inject
    private ClientConfig clientConfig;

    @Override
    public ArgsLogin newArgsInstance() {
        return new ArgsLogin();
    }

    @Override
    public void handleArgs(ArgsLogin args) {
        Logger.info("login args: {}", args);
        HashMap<String, String> params = new HashMap<>();
        params.put("account", args.getAccount());
        params.put("password", args.getPassword());
        LoginResDTO loginRes = HttpClient.restPost(clientConfig.getLoginUrl(), params, LoginResDTO.class);
        if (!Boolean.TRUE.equals(loginRes.getSuccess())) {
            Logger.error("登录失败: {}", loginRes.getMessage());
            return;
        }
        String authToken = loginRes.getAuthToken();
        Logger.info("登录成功: {}", authToken);
    }

}
