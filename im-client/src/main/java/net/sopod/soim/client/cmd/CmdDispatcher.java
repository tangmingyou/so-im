package net.sopod.soim.client.cmd;

import com.beust.jcommander.JCommander;
import com.google.inject.Injector;
import net.sopod.soim.client.logger.Logger;

/**
 * CommandDispatcher
 *
 * auth -u prometheus -p 123456
 * send zeus hello
 * send prometheus hi!
 * users
 * groups
 *
 *
 * @author tmy
 * @date 2022-04-25 10:28
 */
public class CmdDispatcher {

    private final Injector injector;

    public CmdDispatcher(Injector injector) {
        this.injector = injector;
    }

    public void dispatchCmd(String cmdStr) {
        Logger.info("dispatch cmd: {}", cmdStr);
        int idx = cmdStr.indexOf(" ");
        String cmd = idx == -1 ? cmdStr : cmdStr.substring(0, idx);
        String argsStr = idx == -1 ? "" : cmdStr.substring(idx + 1);

    }

    public static void main(String[] args) {
        String cmdStr = "auth -u prometheus -p 123456";
        int idx = cmdStr.indexOf(" ");
        String cmd = idx == -1 ? cmdStr : cmdStr.substring(0, idx);
        String argsStr = idx == -1 ? "" : cmdStr.substring(idx + 1);


        ArgsLogin argsLogin = new ArgsLogin();
        String[] argv = {"-u", "zeus", "-p", "123456"};
        JCommander.newBuilder()
                .addObject(argsLogin)
                .build()
                .parse(argv);
        System.out.println(argsLogin);
    }

}
