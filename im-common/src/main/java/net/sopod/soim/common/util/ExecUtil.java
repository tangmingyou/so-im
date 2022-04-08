package net.sopod.soim.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * ExecUtil
 *
 * @author tmy
 * @date 2022-04-08 16:46
 */
public class ExecUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExecUtil.class);

    public static void exec(String commandLine, String[] envs, File execDir) {
        Thread errThread = null;
        try {
            Process process = Runtime.getRuntime().exec(commandLine, envs, execDir);
            processPrint("INFO", process.getInputStream());
            errThread = new Thread(() -> {
                try {
                    processPrint("ERROR", process.getErrorStream());
                } catch (IOException e) {
                    logger.error("process errorStream error! ", e);
                }
            });
            errThread.setName("errLog");
            errThread.start();
            int exitCode = process.waitFor(); // exit code: 0=success, 1=error
            errThread.join();
            logger.info("命令执行{}, exitCode={}", exitCode == 0 ? "成功" : "失败", exitCode);
        } catch (Exception e) {
            logger.error("{} execute fail!", commandLine, e);
        } finally {
            if (errThread != null && errThread.isAlive()) {
                errThread.interrupt();
            }
        }
    }

    private static void processPrint(String name, InputStream in) throws IOException {
        BufferedInputStream bIn = new BufferedInputStream(in);
        InputStreamReader inReader = new InputStreamReader(bIn, getSystemCharset());
        BufferedReader reader = new BufferedReader(inReader);
        String line;
        try {
            while (null != (line = reader.readLine())) {
                logger.info("{} {}", name, line);
            }
        } finally {
            reader.close();
            inReader.close();
            bIn.close();
            in.close();
        }
    }

    /**
     * 获取操作系统默认编码
     */
    private static Charset getSystemCharset() {
        try {
            return Charset.forName(System.getProperty("sun.jnu.encoding"));
        }catch (UnsupportedCharsetException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            return StandardCharsets.UTF_8;
        }
    }

}
