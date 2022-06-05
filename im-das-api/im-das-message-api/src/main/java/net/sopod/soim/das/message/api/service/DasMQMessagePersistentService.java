package net.sopod.soim.das.message.api.service;

import net.sopod.soim.das.message.api.entity.ImGroupMessage;
import net.sopod.soim.das.message.api.entity.ImMessage;
import net.sopod.soim.das.message.api.mq.ChatQueueType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * DasPersistentService
 *
 * @author tmy
 * @date 2022-06-02 20:28
 */
public class DasMQMessagePersistentService {

    private final RabbitTemplate rabbitTemplate;

    public DasMQMessagePersistentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void saveImMessage(ImMessage imMessage) {
        rabbitTemplate.convertAndSend(ChatQueueType.IM_MESSAGE.getQueueName(), imMessage);
    }

    public void saveImGroupMessage(ImGroupMessage imGroupMessage) {
        rabbitTemplate.convertAndSend(ChatQueueType.IM_GROUP_MESSAGE.getQueueName(), imGroupMessage);
    }

}
