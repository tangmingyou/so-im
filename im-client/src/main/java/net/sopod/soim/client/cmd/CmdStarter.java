package net.sopod.soim.client.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;

import java.util.Scanner;

/**
 * CmdStarter
 * 命令启动器
 *
 * @author tmy
 * @date 2022-04-25 10:28
 */
@Singleton
public class CmdStarter {

    private final CmdDispatcher cmdDispatcher;

    private Scanner scanner;

    @Inject
    public CmdStarter(CmdDispatcher cmdDispatcher) {
        this.cmdDispatcher = cmdDispatcher;
    }

    public void start() {
        this.close();
        this.scanner = new Scanner(System.in);
        Logger.pre("【client】: ");
        while(this.scanner.hasNextLine()) {
            String cmd = this.scanner.nextLine();
            try {
                this.cmdDispatcher.dispatchCmd(cmd);
            } catch (Exception e) {
                Logger.error("cmd error: ", e.getMessage());
            }
            // 退出
            if (CmdEnum.exit.name().equals(cmd.split("[ \t]+")[0])) {
                Logger.info("bye bye");
                break;
            }
            Logger.pre("【client】: ");
        }
    }

    public void close() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }

    public static void printPre() {
        Logger.pre("【client】: ");
    }

}
