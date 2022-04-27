package net.sopod.soim.client;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import net.sopod.soim.client.cmd.CmdStarter;
import net.sopod.soim.client.config.GuiceIoc;

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

    public static void main(String[] args) {
        // 创建 ioc 容器
        GuiceIoc guiceIoc = new GuiceIoc(ClientMain.class);
        Injector injector = Guice.createInjector(Stage.PRODUCTION, guiceIoc);

        // 启动 cmd scanner
        injector.getInstance(CmdStarter.class).start();
    }

}
