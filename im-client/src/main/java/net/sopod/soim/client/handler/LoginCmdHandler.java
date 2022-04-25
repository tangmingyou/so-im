package net.sopod.soim.client.handler;

import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.ArgsLogin;

/**
 * LoginCmdHandler
 *
 * @author tmy
 * @date 2022-04-25 10:53
 */
@Singleton
public class LoginCmdHandler implements CmdHandler<ArgsLogin> {

    @Override
    public ArgsLogin newArgsInstance() {
        return new ArgsLogin();
    }

    @Override
    public void handleArgs(ArgsLogin args) {

    }

}
