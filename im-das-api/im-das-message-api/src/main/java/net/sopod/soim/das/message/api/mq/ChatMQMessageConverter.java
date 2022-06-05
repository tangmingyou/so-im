package net.sopod.soim.das.message.api.mq;

import net.sopod.soim.common.constant.Consts;
import net.sopod.soim.common.util.Converter;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * ChatMessageConverter
 * 聊天消息到 MQ 生产和消费序列化 {@link ChatQueueType}
 *
 * @author tmy
 * @date 2022-05-30 18:07
 */
public class ChatMQMessageConverter extends AbstractMessageConverter {

    private static final Logger logger = LoggerFactory.getLogger(ChatMQMessageConverter.class);

    public static final String MQ_MSG_TYPE = "MQ_MSG_TYPE";

    public static final String MQ_MSG_IS_SNAPPY = "MQ_MSG_IS_SNAPPY";

    @Override
    protected Message createMessage(Object msg, MessageProperties messageProperties) {
        if (msg instanceof Message) {
            return (Message) msg;
        }
        ChatQueueType chatQueueType = ChatQueueType.getMQType(msg.getClass(),
                type -> new MessageConversionException(String.format("不支持%s消息类型,在MQType添加", type.getName())));
        messageProperties.setHeader(MQ_MSG_TYPE, chatQueueType.ordinal());
        messageProperties.setMessageId(chatQueueType.getMsgId(msg));
        // 消息序列化成字节
        byte[] bytes = Jackson.msgpack().serializeBytes(msg);
        if (bytes.length > Consts.KB) {
            // snappy 压缩
            try {
                long start = ImClock.millis();
                byte[] compressed = Snappy.compress(bytes);
                logger.info("snappy compress msg bytes {} to {}, use {} ms.", bytes.length, compressed.length, ImClock.millis() - start);
                bytes = compressed;
                messageProperties.setHeader(MQ_MSG_IS_SNAPPY, true);
            } catch (IOException e) {
                logger.error("snappy compress error: bytes len {}", bytes.length, e);
            }
        }
        return new Message(bytes, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        MessageProperties messageProperties = message.getMessageProperties();
        Object ordinal = messageProperties.getHeaders().get(MQ_MSG_TYPE);
        Object isSnappy = messageProperties.getHeaders().get(MQ_MSG_IS_SNAPPY);
        String consumerQueue = messageProperties.getConsumerQueue();
        // 获取mq消息类型
        ChatQueueType chatQueueType = ordinal == null
                ? ChatQueueType.getMQType(consumerQueue, queue -> new MessageConversionException(String.format("没有队列为%s的消息类型,在MQType添加", queue)))
                :  ChatQueueType.getMQType(Converter.toInt(ordinal), ori -> new MessageConversionException(String.format("没有ordinal为%d的消息类型,在MQType添加", ori)));
        // 反序列化字节数据
        byte[] body = message.getBody();
        if (Boolean.TRUE.equals(isSnappy)) {
            try {
                body = Snappy.uncompress(body);
            } catch (IOException e) {
                throw new MessageConversionException(String.format("消息设置为snappy压缩但解压失败,消息长度 %d bytes", body.length), e);
            }
        }
        return Jackson.msgpack().deserializeBytes(body, chatQueueType.getMsgType());
    }

}
