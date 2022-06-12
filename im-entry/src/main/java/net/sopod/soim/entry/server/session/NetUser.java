package net.sopod.soim.entry.server.session;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;
import net.sopod.soim.entry.server.handler.ImContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;

public class NetUser {

    private static final Logger logger = LoggerFactory.getLogger(NetUser.class);

    /** channel 绑定 netUser 对象 */
    public static final AttributeKey<NetUser> NET_USER_KEY = AttributeKey.valueOf(NetUser.class, "NET_USER");

    protected final WeakReference<Channel> channel;

    public NetUser(Channel channel) {
        this.channel = new WeakReference<>(channel);
    }

    public boolean isAccount() {
        return false;
    }

    public boolean isMonitor() {
        return false;
    }

    public boolean isActive() {
        Channel chan = this.channel.get();
        return chan != null && chan.isActive();
    }

    public Channel channel() {
        return this.channel.get();
    }

    /**
     * 登录后设置为账号
     */
    public void upgradeAccount(Account account) {
        Channel chan = this.channel.get();
        if (chan != null) {
            chan.attr(NET_USER_KEY).set(account);
        }
    }

    /**
     * 设置连接为 monitor
     */
    public void upgradeMonitor(Monitor monitor) {
        Channel chan = this.channel.get();
        if (chan != null) {
            chan.attr(NET_USER_KEY).set(monitor);
        }
    }

    /**
     * 客户端通过 dispatcher 消费
     */
    public void writeNow(MessageLite message) {
        write0(null, message);
    }

    /**
     * 客户端可同步消费，根据请求id对应响应
     */
    public void writeNow(ImContext ctx, MessageLite message) {
        write0(ctx.getSerialNo(), message);
    }

    private void write0(Integer serialNo, MessageLite message) {
        Channel channel = this.channel.get();
        if (channel == null) {
            logger.error("channel closed drop message: {}, {}", serialNo, message);
            return;
        }
        // 构建 ImMessage
        ImMessage imMessage = ImMessageCodec.encodeImProto(message);
        imMessage.setSerialNo(serialNo == null ? -1 : serialNo);
        channel.writeAndFlush(imMessage);
    }

    @Override
    public String toString() {
        return "NetUser{" +
                "channel=" + channel +
                '}';
    }

}
