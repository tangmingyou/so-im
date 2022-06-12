package net.sopod.soim.entry.server.session;

import io.netty.channel.Channel;

/**
 * Monitor
 *
 * @author tmy
 * @date 2022-06-11 14:31
 */
public class Monitor extends NetUser {

    private long lastActive;

    public Monitor(Channel channel) {
        super(channel);
    }

    @Override
    public boolean isMonitor() {
        return true;
    }

    public void setLastActive(long lastActive) {
        this.lastActive = lastActive;
    }

    public long getLastActive() {
        return lastActive;
    }

}
