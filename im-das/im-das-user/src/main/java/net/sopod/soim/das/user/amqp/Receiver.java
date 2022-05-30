package net.sopod.soim.das.user.amqp;

import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
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
@Component
@RabbitListener(queues = "hello")
public class Receiver {

    @RabbitHandler
    public void process(Message message) {
        byte[] body = message.getBody();
        ImMessage imMessage = Jackson.msgpack().deserializeBytes(body, ImMessage.class);
        System.out.println("Receiver: " + imMessage);
    }

    @RabbitHandler
    public void process(byte[] payload) {
        ImMessage imMessage = Jackson.msgpack().deserializeBytes(payload, ImMessage.class);
        System.out.println("receive2: " + imMessage);
    }

}
