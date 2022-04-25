package net.sopod.soim.client.cmd;

import com.google.inject.Injector;
import net.sopod.soim.client.logger.Logger;

import java.util.Scanner;

/**
 * Args
 *
 * @author tmy
 * @date 2022-04-25 10:28
 */
public class CmdStarter {

    private final CmdDispatcher cmdDispatcher;

    public CmdStarter(CmdDispatcher cmdDispatcher) {
        this.cmdDispatcher = cmdDispatcher;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Logger.pre("[client]: ");
        while(scanner.hasNextLine()) {
            String cmd = scanner.nextLine();
            cmdDispatcher.dispatchCmd(cmd);
            Logger.pre("[client]: ");
        }

        scanner.close();
    }

    public void exit() {

    }

}
