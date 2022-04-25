package net.sopod.soim.client.logger;

/**
 * Logger
 *
 * @author tmy
 * @date 2022-04-25 11:44
 */
public class Logger {

    public static void info(String msg, Object...args) {
        msg = msg.replaceAll("\\{}", "%s");
        for (int i = 0; i < args.length; i++) {
            args[i] = String.valueOf(args[i]);
        }
        System.out.println("[info]: " + String.format(msg, args));
    }

    public static void pre(String pre) {
        System.out.print(pre);
    }

}
