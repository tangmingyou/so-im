package net.sopod.soim.das.user.api.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

/**
 * ChatRabbitMQAutoConfiguration
 *
 * @author tmy
 * @date 2022-06-02 10:01
 */
@ConditionalOnClass(name = "org.springframework.amqp.core.Queue")
@Import(ChatPersistentRabbitMQConfiguration.class)
public class ChatMQAutoConfiguration {

}
