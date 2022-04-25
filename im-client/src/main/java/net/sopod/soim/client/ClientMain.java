package net.sopod.soim.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import net.sopod.soim.client.cmd.CmdDispatcher;
import net.sopod.soim.client.cmd.CmdStarter;
import net.sopod.soim.client.config.GuiceIoc;
import net.sopod.soim.client.net.ImNetClient;

/**
 * Main
 * jcommander:
 *   https://www.codenong.com/b-jcommander-parsing-command-line-parameters/
 *   https://www.mianshigee.com/project/jcommander
 *
 * @author tmy
 * @date 2022-03-27 22:32
 */
public class ClientMain {

    public static void main(String[] args) throws InterruptedException {
//        ImNetClient client = new ImNetClient();
//        client.connect("127.0.0.1", 8088);
        GuiceIoc guiceIoc = new GuiceIoc(ClientMain.class);
        Injector injector = Guice.createInjector(Stage.PRODUCTION, guiceIoc);
        // dispatcher
        CmdDispatcher cmdDispatcher = new CmdDispatcher(injector);
        // starter
        CmdStarter cmdStarter = new CmdStarter(cmdDispatcher);
        cmdStarter.start();
    }

}
