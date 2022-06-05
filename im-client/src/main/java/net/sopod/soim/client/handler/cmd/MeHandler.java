package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.handler.NonArgsHandler;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.session.SoImSession;

/**
 * MeHandler
 *
 * @author tmy
 * @date 2022-06-02 15:49
 */
@Singleton
public class MeHandler extends NonArgsHandler {

    @Inject
    private SoImSession soImSession;

    @Override
    public void handle() {
        Long uid = soImSession.getUid();
        String account = soImSession.getAccount();
        Console.info("me: " + uid + "|" + account);
    }

}
