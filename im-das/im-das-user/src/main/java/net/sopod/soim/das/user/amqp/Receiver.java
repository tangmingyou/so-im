package net.sopod.soim.das.user.amqp;

import lombok.AllArgsConstructor;
import net.sopod.soim.das.user.api.model.entity.ImGroupMessage;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import net.sopod.soim.das.user.api.mq.ChatQueue;
import net.sopod.soim.das.user.dao.ImGroupMessageMapper;
import net.sopod.soim.das.user.dao.ImMessageMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Listener
 * 只能接受字符串和字节数组
 *
 * @author tmy
 * @date 2022-05-28 16:26
 */
@Service
@AllArgsConstructor
@RabbitListener(queues = {
        ChatQueue.IM_MESSAGE_PERSISTENT,
        ChatQueue.IM_GROUP_MESSAGE_PERSISTENT
})
public class Receiver {

    ImMessageMapper imMessageMapper;

    ImGroupMessageMapper imGroupMessageMapper;

    @RabbitHandler
    public void process(ImGroupMessage imGroupMessage) {
        System.out.println("receiver2: " + imGroupMessage);
        imGroupMessageMapper.insert(imGroupMessage);
    }

    @RabbitHandler
    public void process(ImMessage imMessage) {
        System.out.println("receive3: " + imMessage);
        imMessageMapper.insert(imMessage);
    }

}
