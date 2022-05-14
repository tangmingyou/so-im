package net.sopod.soim.entry.config;

import net.sopod.soim.entry.registry.ProtoMessageHandlerRegistry;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * ApplicationContextInitialed
 *
 * @author tmy
 * @date 2022-04-10 22:20
 */
@Configuration
public class SpringApplicationContextInitialed implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ImEntryAppContextHolder.setContext(applicationContext);

        // 注册 protobuf 消息 handler
        ProtoMessageHandlerRegistry.registerHandlerWithApplicationContext(applicationContext);
    }

}
