package net.sopod.soim.das.user.api.mq;

import net.sopod.soim.common.constant.Consts;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.das.user.api.model.entity.ImMessage;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;

/**
 * ChatMessageConverter
 *
 * @author tmy
 * @date 2022-05-30 18:07
 */
public class ChatMessageConverter extends AbstractMessageConverter {

    @Override
    protected Message createMessage(Object object, MessageProperties messageProperties) {
        if (object instanceof Message) {
            return (Message) object;
        }
        byte[] bytes = Jackson.msgpack().serializeBytes(object);
        if (bytes.length > 100 * Consts.KB) {
            // TODO 压缩
        }

        return null;
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        byte[] body = message.getBody();
        Jackson.msgpack().deserializeBytes(body, ImMessage.class);
        return null;
    }

}
