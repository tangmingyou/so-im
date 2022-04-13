package net.sopod.soim.core.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.lang.ref.WeakReference;

public class NetUser {

    /** channel 绑定 netUser 对象 */
    public static final AttributeKey<NetUser> NET_USER_KEY = AttributeKey.valueOf(NetUser.class, "NET_USER");

    protected final WeakReference<Channel> channel;

    public NetUser(Channel channel) {
        this.channel = new WeakReference<>(channel);
    }

    public boolean isAccount() {
        return false;
    }

    public boolean isActive() {
        Channel chan = this.channel.get();
        return chan != null && chan.isActive();
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

    public void write(Object message) {
        write(message, false);
    }

    public void writeNow(Object message) {
        write(message, true);
    }

    private void write(Object message, boolean now) {
        Channel channel = this.channel.get();
        if (channel == null) {
            return;
        }
        if (now) {
            channel.writeAndFlush(message);
        } else {
            channel.write(message);
        }
    }

    @Override
    public String toString() {
        return "NetUser{" +
                "channel=" + channel +
                '}';
    }

}
