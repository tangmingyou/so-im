package net.sopod.soim.entry.server;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import net.sopod.soim.core.handler.MessageHandler;
import net.sopod.soim.core.net.AttributeKeys;
import net.sopod.soim.core.registry.ProtoMessageHandlerRegistry;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.entry.worker.Worker;
import net.sopod.soim.entry.worker.WorkerGroup;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * MessageLiteHandler
 *
 * @author tmy
 * @date 2022-04-10 22:40
 */
public class InboundImMessageHandler extends SimpleChannelInboundHandler<MessageLite> {

    /**
     * channel 建立连接，设置初始属性
     * TODO 登录倒计时 5s 断开连接, 登录失败次数
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.attr(NetUser.NET_USER_KEY).set(new NetUser(channel));
        channel.attr(AttributeKeys.WRITE_FAIL_TIMES).set(new AtomicInteger());
        channel.attr(AttributeKeys.LOGIN_FAIL_TIMES).set(new AtomicInteger());

        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Attribute<NetUser> netUser = ctx.channel().attr(NetUser.NET_USER_KEY);

        ctx.fireChannelInactive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageLite messageLite) throws Exception {
        Attribute<NetUser> netUserAttr = ctx.channel().attr(NetUser.NET_USER_KEY);
        NetUser netUser = netUserAttr.get();
        // TODO dispatcher
        ProtoMessageDispatcher.dispatch(netUser, messageLite);
    }

}
