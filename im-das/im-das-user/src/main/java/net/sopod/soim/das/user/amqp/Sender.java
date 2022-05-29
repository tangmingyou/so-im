package net.sopod.soim.das.user.amqp;

import net.sopod.soim.common.util.ImClock;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Sender
 *
 * @author tmy
 * @date 2022-05-28 16:23
 */
@Component
public class Sender implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        AmqpTemplate amqpTemplate = context.getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend("hello", "hello:" + ImClock.millis());
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(UUID.randomUUID().toString());
        Message message = new Message(new byte[] {}, messageProperties);

        System.out.println("send msg....");
    }

}
