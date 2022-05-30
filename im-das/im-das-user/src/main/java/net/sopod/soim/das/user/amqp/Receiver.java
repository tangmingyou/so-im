package net.sopod.soim.das.user.amqp;

import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.das.user.api.model.entity.ImGroupMessage;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import net.sopod.soim.das.user.api.mq.ChatQueue;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listener
 * 只能接受字符串和字节数组
 *
 * @author tmy
 * @date 2022-05-28 16:26
 */
@RabbitListener(queues = {ChatQueue.IM_MESSAGE_PERSISTENT, ChatQueue.IM_GROUP_MESSAGE_PERSISTENT})
@Component
public class Receiver {

    @RabbitHandler
    public void process(ImGroupMessage imGroupMessage) {
        System.out.println("receiver2: " + imGroupMessage);
    }

    @RabbitHandler
    public void process(ImMessage imMessage) {
        System.out.println("receive3: " + imMessage);
    }

}
