package net.sopod.soim.entry.server;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.entry.server.session.NetUser;
import net.sopod.soim.data.msg.task.Tasks;
import net.sopod.soim.entry.delay.NetUserDelayTaskManager;

import java.util.concurrent.TimeUnit;
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
        NetUser netUser = new NetUser(channel);
        channel.attr(NetUser.NET_USER_KEY).set(netUser);
        channel.attr(AttributeKeys.WRITE_FAIL_TIMES).set(new AtomicInteger());
        channel.attr(AttributeKeys.LOGIN_FAIL_TIMES).set(new AtomicInteger());

        // 倒计时 10s 未登录断开连接, 登录失败次数
        Tasks.NetUserDelayCloseTask taskMsg = Tasks.NetUserDelayCloseTask.newBuilder()
                .setTime(ImClock.millis())
                .build();
        NetUserDelayTaskManager.addTask(netUser, taskMsg, 10, TimeUnit.SECONDS);

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
