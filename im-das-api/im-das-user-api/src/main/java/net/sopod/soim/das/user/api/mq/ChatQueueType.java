package net.sopod.soim.das.user.api.mq;

import net.sopod.soim.common.util.Converter;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.user.api.model.entity.ImGroupMessage;
import net.sopod.soim.das.user.api.model.entity.ImMessage;

import java.util.function.Function;

/**
 * MQType
 *
 * @author tmy
 * @date 2022-05-30 17:43
 */
public enum ChatQueueType {

    /** 单聊消息 */
    IM_MESSAGE(ChatQueue.IM_MESSAGE_PERSISTENT,
            ImMessage.class,
            msg -> Converter.toString(((ImMessage)msg).getId())),

    /** 群聊消息 */
    IM_GROUP_MESSAGE(ChatQueue.IM_GROUP_MESSAGE_PERSISTENT,
            ImGroupMessage.class,
            msg -> Converter.toString(((ImGroupMessage)msg).getId()));

    private final Class<?> msgType;

    private final String queueName;

    private final Function<Object, String> msgIdConverter;

    public static final String TOPIC_CHAT_PERSISTENT = "topic.chat_persistent";

    ChatQueueType(String queueName, Class<?> msgType, Function<Object, String> msgIdConverter) {
        this.queueName = queueName;
        this.msgType = msgType;
        this.msgIdConverter = msgIdConverter;
    }

    public Class<?> getMsgType() {
        return msgType;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getMsgId(Object msg) {
        String msgId = msgIdConverter.apply(msg);
        return msgId != null ? msgId : StringUtil.randomUUID();
    }

    public static ChatQueueType getMQType(Class<?> msgType, Function<Class<?>, RuntimeException> exceptionProvider) {
        ChatQueueType[] values = values();
        for (ChatQueueType type : values) {
            if (type.msgType == msgType) {
                return type;
            }
        }
        throw exceptionProvider.apply(msgType);
    }

    public static ChatQueueType getMQType(int ordinal, Function<Integer, RuntimeException> exceptionProvider) {
        ChatQueueType[] values = values();
        if (ordinal < values.length) {
            return values[ordinal];
        }
        throw exceptionProvider.apply(ordinal);
    }

    public static ChatQueueType getMQType(String queueName, Function<String, RuntimeException> exceptionProvider) {
        ChatQueueType[] values = values();
        for (ChatQueueType queueType : values) {
            if (queueType.getQueueName().equals(queueName)) {
                return queueType;
            }
        }
        throw exceptionProvider.apply(queueName);
    }

}
