package net.sopod.soim.entry.registry;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.Reflects;
import net.sopod.soim.entry.server.handler.MessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * ProtoMessageDispatcher
 * implements ApplicationContextAware
 *
 * @author tmy
 * @date 2022-04-10 19:15
 */
public class ProtoMessageHandlerRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ProtoMessageHandlerRegistry.class);

    private static final Map<Class<?>, MessageHandler<?>> TYPE_HANDLER_MAP = new HashMap<>(32);

    private static final CountDownLatch CONTEXT_AWARE_AWAIT = new CountDownLatch(1);

    /**
     * spring ioc 容器中获取 msgType handler
     * @param context spring ioc 上下文
     */
    public static synchronized void registerHandlerWithApplicationContext(ApplicationContext context) {
        if (CONTEXT_AWARE_AWAIT.getCount() <= 0) {
            throw new IllegalStateException("proto message registry already initialed!");
        }
        logger.info("proto message registry initial...");
        long start = ImClock.millis();
        Map<String, MessageHandler> beansOfType = context.getBeansOfType(MessageHandler.class);
        Collection<MessageHandler> handlers = beansOfType.values();
        for (MessageHandler<?> handler : handlers) {
            // 获取 handler 泛型
            List<String> genericTypes = Reflects.getSuperClassGenericTypes(handler.getClass());
            try {
                Class<?> type = genericTypes.size() == 0 ? Object.class : Class.forName(genericTypes.get(0));
                MessageHandler<?> existHandler = TYPE_HANDLER_MAP.putIfAbsent(type, handler);
                logger.debug("registe msg type {} for handler {}", type, handler);
                if (existHandler != null) {
                    // 消息类型有重复的 handler!
                    throw new IllegalStateException("msg type " + type + " handler duplicate; " +
                            "[" + existHandler.getClass() + "] and [" + handler.getClass() + "]");
                }
            } catch (ClassNotFoundException e) {
                logger.warn("handler msgType class not found!", e);
            }
        }
        CONTEXT_AWARE_AWAIT.countDown();
        logger.info("proto message registry complete, {} handlers at {}ms.", handlers.size(), ImClock.millis() - start);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> MessageHandler<T> getTypeHandler(Class<? extends MessageLite> type) {
        if (CONTEXT_AWARE_AWAIT.getCount() > 0) {
            try {
                CONTEXT_AWARE_AWAIT.await();
            } catch (InterruptedException e) {
                logger.error("proto message type dispatcher, wait context ready error!", e);
            }
        }
        return (MessageHandler<T>) TYPE_HANDLER_MAP.get(type);
    }

}
