package net.sopod.soim.das.user.api.mq;

import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

/**
 * ChatMsg
 *
 * @author tmy
 * @date 2022-05-30 15:03
 */
public class ChatMsg extends Message {

    public ChatMsg(ImMessage imMessage) {
        super(Jackson.msgpack().serializeBytes(imMessage), getMessageProperties(imMessage));
    }

    private static MessageProperties getMessageProperties(ImMessage imMessage) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(StringUtil.toString(imMessage.getId()));
        return messageProperties;
    }

}
