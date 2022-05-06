package net.sopod.soim.entry.config;

import org.springframework.context.ApplicationContext;

/**
 * ContextHandler
 *
 * @author tmy
 * @date 2022-04-28 15:06
 */
public class ImEntryAppContextHolder {

    private static ApplicationContext applicationContext;

    private static String dubboAppServiceAddr;

    public static void setContext(ApplicationContext applicationContext) {
        ImEntryAppContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

    public static void setDubboAppServiceAddr(String dubboAppServiceAddr) {
        ImEntryAppContextHolder.dubboAppServiceAddr = dubboAppServiceAddr;
    }

    public static String getDubboAppServiceAddr() {
        return dubboAppServiceAddr;
    }

}