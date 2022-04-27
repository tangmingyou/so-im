package net.sopod.soim.client.logger;

import net.sopod.soim.client.cmd.CmdStarter;

/**
 * Logger
 *
 * @author tmy
 * @date 2022-04-25 11:44
 */
public class Logger {

    public static void info(String msg, Object...args) {
        log("[info] " + msg, args);
    }

    public static void error(String msg, Object...args) {
        log("[error] " + msg, args);
    }

    public static void log(String msg, Object...args) {
        msg = msg.replaceAll("\\{}", "%s");
        for (int i = 0; i < args.length; i++) {
            args[i] = String.valueOf(args[i]);
        }
        if ("main".equals(Thread.currentThread().getName())) {
            System.out.println(String.format(msg, args));
        } else {
            // 其他线程异步打印
            System.out.println(System.lineSeparator() + String.format(msg, args));
            CmdStarter.printPre();
        }
    }

    public static void pre(String pre) {
        System.out.print(pre);
    }

}