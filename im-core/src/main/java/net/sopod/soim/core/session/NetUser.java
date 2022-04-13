package net.sopod.soim.core.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

public class NetUser {

    /** channel 绑定 netUser 对象 */
    public static final AttributeKey<NetUser> NET_USER_KEY = AttributeKey.valueOf(NetUser.class, "NET_USER");

    private final WeakReference<Channel> channel;

    private final AtomicBoolean isActive = new AtomicBoolean(true);

    public NetUser(Channel channel) {
        this.channel = new WeakReference<>(channel);
    }

    public boolean isAccount() {
        return false;
    }

    public boolean isActiveChannel() {
        Channel chan = this.channel.get();
        return chan != null && chan.isActive();
    }

    public boolean isActive() {
        return this.isActive.get();
    }

    public void inactive() {
        this.isActive.set(false);
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
