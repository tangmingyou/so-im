package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsUsers;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.data.msg.user.UserGroup;

/**
 * UsersHandler
 *
 * @author tmy
 * @date 2022-04-28 12:48
 */
@Singleton
public class UsersHandler implements CmdHandler<ArgsUsers> {

    @Inject
    private SoImSession soImSession;

    @Override
    public ArgsUsers newArgsInstance() {
        return new ArgsUsers();
    }

    @Override
    public void handleArgs(ArgsUsers args) {
        UserGroup.ReqOnlineUserList req = UserGroup.ReqOnlineUserList.newBuilder()
                .setKeyword(args.getKeyword() != null ? args.getKeyword() : "")
                .build();
        soImSession.send(req);
    }

}
