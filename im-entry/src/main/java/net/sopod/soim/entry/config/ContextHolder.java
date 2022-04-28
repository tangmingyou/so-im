package net.sopod.soim.entry.config;

import org.springframework.context.ApplicationContext;

/**
 * ContextHandler
 *
 * @author tmy
 * @date 2022-04-28 15:06
 */
public class ContextHolder {

    private static ApplicationContext applicationContext;

    public static void setContext(ApplicationContext applicationContext) {
        ContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> beanType) {
        return applicationContext.getBean(beanType);
    }

}
