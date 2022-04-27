package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.CmdStarter;
import net.sopod.soim.client.cmd.handler.NonArgsHandler;
import net.sopod.soim.client.session.SoImSession;

/**
 * ExitHandler
 *
 * @author tmy
 * @date 2022-04-25 11:53
 */
@Singleton
public class ExitHandler extends NonArgsHandler {

    @Inject
    private SoImSession soImSession;

    @Inject
    private CmdStarter cmdStarter;

    @Override
    public void handle() {
        cmdStarter.close();
        soImSession.close();
    }

}
