package net.sopod.soim.entry.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.Attribute;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.entry.config.ImEntryAppContext;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
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
public class InboundImMessageHandler extends SimpleChannelInboundHandler<ImMessage> {

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
        NetUserDelayTaskManager.addTask(netUser, taskMsg, 6, TimeUnit.SECONDS);

        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NetUser netUser = ctx.channel().attr(NetUser.NET_USER_KEY).get();
        // 取消登录用户状态
        if (netUser != null && netUser.isAccount()) {
            AccountRegistry registry = ImEntryAppContext.getBean(AccountRegistry.class);
            registry.remove(((Account) netUser).getUid());
        }
        // TODO 离线状态同步到 im-router，等待30s不重连，删除im-router用户缓存数据
        ctx.fireChannelInactive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImMessage imMessage) throws Exception {
        Attribute<NetUser> netUserAttr = ctx.channel().attr(NetUser.NET_USER_KEY);
        NetUser netUser = netUserAttr.get();
        ImContext execCtx = new ImContext(imMessage.getSerialNo());
        // TODO dispatcher
        ProtoMessageDispatcher.dispatch(execCtx, netUser, imMessage.getDecodeBody());
    }

}
