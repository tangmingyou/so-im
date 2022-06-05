package net.sopod.soim.das.message.consumer;

import lombok.AllArgsConstructor;
import net.sopod.soim.das.message.api.entity.ImGroupMessage;
import net.sopod.soim.das.message.api.entity.ImMessage;
import net.sopod.soim.das.message.api.mq.ChatQueue;
import net.sopod.soim.das.message.dao.ImGroupMessageMapper;
import net.sopod.soim.das.message.dao.ImMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ImMessagePersistentListener {

    private static final Logger logger = LoggerFactory.getLogger(ImMessagePersistentListener.class);

    ImMessageMapper imMessageMapper;

    ImGroupMessageMapper imGroupMessageMapper;

    @RabbitHandler
    public void process(ImGroupMessage imGroupMessage) {
        // System.out.println("receiver2: " + imGroupMessage);
        try {
            imGroupMessageMapper.insert(imGroupMessage);
        } catch (Exception e) {
            logger.error("ImGroupMessage insert 失败: {}", imGroupMessage);
        }
    }

    @RabbitHandler
    public void process(ImMessage imMessage) {
        // System.out.println("receive3: " + imMessage);
        try {
            imMessageMapper.insert(imMessage);
        } catch (Exception e) {
            logger.error("ImMessage insert 失败: {}", imMessage);
        }
    }

}
