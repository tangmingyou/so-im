package net.sopod.soim.client.logger;

import com.google.common.base.Joiner;
import net.sopod.soim.client.cmd.CmdStarter;
import net.sopod.soim.client.cmd.console.ConsoleBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Logger
 *
 * @author tmy
 * @date 2022-04-25 11:44
 */
public class Console {

    private static int consoleWidth = 60;

    public static void info(String msg, Object...args) {
        log("info ", msg, args);
    }

    public static void error(String msg, Object...args) {
        log("error ", msg, args);
    }

    /**
     * TODO ConsoleBox 中文尺寸处理
     */
    public static void log(String key, String msg, Object...args) {
        msg = msg.replaceAll("\\{}", "%s");
        for (int i = 0; i < args.length; i++) {
            args[i] = String.valueOf(args[i]);
        }
//        if ("main".equals(Thread.currentThread().getName())) {
//            System.out.println(String.format(msg, args));
//        } else {
//            // 其他线程异步打印
//            System.out.println(System.lineSeparator() + String.format(msg, args));
//            CmdStarter.printPre();
//        }
        ConsoleBox box = new ConsoleBox(consoleWidth);
        box.title("提示");
        box.line(key, String.format(msg, args));
        box.build(System.out);
    }

    public static void logList(String key, List<String> lines) {
        ConsoleBox box = new ConsoleBox(consoleWidth);
        box.title("");
        String line = Joiner.on("\n").join(lines);
        box.line(key, "\n" + line);
        box.build(System.out);
    }

    public static void pre(String pre) {
        System.out.print(pre);
    }

    public static void main(String[] args) {
        System.out.println(Joiner.on(System.lineSeparator()).join(Arrays.asList("a", "b", "c")));
    }

}
