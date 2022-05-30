package net.sopod.soim.das.user.api.mq;

import net.sopod.soim.das.user.api.model.entity.ImGroupMessage;
import net.sopod.soim.das.user.api.model.entity.ImMessage;

/**
 * MQType
 *
 * @author tmy
 * @date 2022-05-30 17:43
 */
public enum MQType {

    /** 单聊消息 */
    IM_MESSAGE("IM_MESSAGE", ImMessage.class),

    /** 群聊消息 */
    IM_GROUP_MESSAGE("IM_GROUP_MESSAGE", ImGroupMessage.class);

    private final Class<?> beanClazz;

    private final String queueName;

    MQType(String queueName, Class<?> beanClazz) {
        this.queueName = queueName;
        this.beanClazz = beanClazz;
    }

    public Class<?> getBeanClazz() {
        return beanClazz;
    }

    public String getQueueName() {
        return queueName;
    }

    public static MQType getMQType(Class<?> beanClazz) {
        MQType[] values = values();
        for (MQType type : values) {
            if (type.beanClazz == beanClazz) {
                return type;
            }
        }
        return null;
    }

}
