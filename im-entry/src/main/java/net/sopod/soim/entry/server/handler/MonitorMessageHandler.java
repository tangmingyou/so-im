package net.sopod.soim.entry.server.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.entry.server.session.Monitor;
import net.sopod.soim.entry.server.session.NetUser;

/**
 * MonitorMessageHandler
 *
 * @author tmy
 * @date 2022-06-11 14:41
 */
public abstract class MonitorMessageHandler<T> implements MessageHandler<T> {

    @Override
    public void exec(ImContext ctx, NetUser netUser, T req) {
        if (!netUser.isMonitor()) {
            throw new SoimException("NetUser is not Monitor!" + netUser);
        }
        MessageLite res = this.handle(ctx, (Monitor) netUser, req);
        if (res != null) {
            netUser.writeNow(ctx, res);
        }
    }

    public abstract MessageLite handle(ImContext ctx, Monitor monitor, T req);

}
