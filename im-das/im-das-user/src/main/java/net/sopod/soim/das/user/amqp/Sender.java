package net.sopod.soim.das.user.amqp;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import net.sopod.soim.das.user.api.mq.ChatMsg;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

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
        // amqpTemplate.convertAndSend("hello", "hello:" + ImClock.millis());
        ImMessage imMessage = new ImMessage()
                .setId(10086L)
                .setSender(10808L)
                .setReceiver(180002L)
                .setFriendId(101010L)
                .setContent("何时")
                .setCreateTime(ImClock.date());
        ChatMsg chatMsg = new ChatMsg(imMessage);
        amqpTemplate.convertAndSend("hello", 123);
        amqpTemplate.send("hello", chatMsg);
        System.out.println("send msg....");
    }

}
