package net.sopod.soim.das.user.api.service;

import net.sopod.soim.das.user.api.model.entity.ImGroupMessage;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import net.sopod.soim.das.user.api.mq.ChatQueueType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * DasPersistentService
 *
 * @author tmy
 * @date 2022-06-02 20:28
 */
public class DasMQPersistentService {

    private final RabbitTemplate rabbitTemplate;

    public DasMQPersistentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void saveImMessage(ImMessage imMessage) {
        rabbitTemplate.convertAndSend(ChatQueueType.IM_MESSAGE.getQueueName(), imMessage);
    }

    public void saveImGroupMessage(ImGroupMessage imGroupMessage) {
        rabbitTemplate.convertAndSend(ChatQueueType.IM_GROUP_MESSAGE.getQueueName(), imGroupMessage);
    }

}
