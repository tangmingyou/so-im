package net.sopod.soim.entry.config;

import org.springframework.context.ApplicationContext;

/**
 * ContextHandler
 *
 * @author tmy
 * @date 2022-04-28 15:06
 */
public class ImEntryAppContext {

    private static ApplicationContext applicationContext;

    private static String appAddr;

    private static String appHost;

    private static int appPort;

    public static void setContext(ApplicationContext applicationContext) {
        ImEntryAppContext.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    public static void setAppServiceAddr(String host, int port) {
        ImEntryAppContext.appAddr = host + ":" + port;
        ImEntryAppContext.appHost = host;
        ImEntryAppContext.appPort = port;
    }

    public static String getEntryAppAddr() {
        return appAddr;
    }

    public static String getAppHost() {
        return appHost;
    }

    public static int getAppPort() {
        return appPort;
    }

}
