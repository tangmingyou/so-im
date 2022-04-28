package net.sopod.soim.client.session;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.MessageLite;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.common.util.Reflects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MessageDispatcher
 *
 * @author tmy
 * @date 2022-04-27 9:48
 */
@Singleton
@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<MessageLite> {

    private Map<Class<?>, MessageHandler> msgHandlers;

    @Inject
    public MessageDispatcher(Set<MessageHandler> handlers) {
        this.registryHandlers(handlers);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageLite msg) throws Exception {
        MessageHandler messageHandler = msgHandlers.get(msg.getClass());
        messageHandler.handleMsg(msg);
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

}
