package net.sopod.soim.das.user.api.config;

import net.sopod.soim.das.user.api.mq.ChatMQMessageConverter;
import net.sopod.soim.das.user.api.mq.ChatQueueType;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ChatPersistentRabbitMQConfiguration
 * RabbitMQ的配置类，用来配置队列、交换器、路由等高级信息
 *
 * @author tmy
 * @date 2022-05-30 23:14
 */
@Configuration
public class ChatPersistentRabbitMQConfiguration implements ImportBeanDefinitionRegistrar {

    /**
     * direct直连模式, 按照routingkey分发到指定队列
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 消息队列Queue绑定
        for (ChatQueueType chatQueueType : ChatQueueType.values()) {
            Queue queue = new Queue(chatQueueType.getQueueName(), true);
            RootBeanDefinition queueDef = new RootBeanDefinition();
            queueDef.setInstanceSupplier(() -> queue);
            queueDef.setBeanClass(Queue.class);
            registry.registerBeanDefinition(queue.getName(), queueDef);
        }
    }

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new ChatMQMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}
