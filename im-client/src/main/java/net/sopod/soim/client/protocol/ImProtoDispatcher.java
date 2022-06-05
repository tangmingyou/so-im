package net.sopod.soim.client.protocol;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.common.util.Reflects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MessageDispatcher
 * 通过 protobuf 消息类型分发消息到 MessageHandler
 *
 * @author tmy
 * @date 2022-04-27 9:48
 */
@Singleton
public class ImProtoDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(ImProtoDispatcher.class);

    private Map<Class<?>, MessageHandler> msgHandlers;

    @Inject
    public ImProtoDispatcher(Set<MessageHandler> handlers) {
        this.registryHandlers(handlers);
    }

    private void registryHandlers(Set<MessageHandler> handlers) {
        Map<Class<?>, MessageHandler> msgHandlers = new HashMap<>();
        for (MessageHandler<?> handler : handlers) {
            List<String> msgTypes = Reflects.getSuperInterfaceGenericTypes(handler.getClass());
            try {
                Class<?> msgType = msgTypes.size() == 0 ? Object.class : Class.forName(msgTypes.get(0));
                msgHandlers.put(msgType, handler);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.msgHandlers = msgHandlers;
    }

    @SuppressWarnings("unchecked")
    public void dispatch(MessageLite msg) {
        MessageHandler<Object> messageHandler = msgHandlers.get(msg.getClass());
        if (messageHandler == null) {
            logger.error("{} 消息, 无对应MessageHandler", msg.getClass());
            return;
        }
        messageHandler.handleMsg(msg);
    }

}
