package net.sopod.soim.entry.server.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.server.session.NetUser;

/**
 * NetUserMessageHandler
 *
 * @author tmy
 * @date 2022-04-10 23:40
 */
public abstract class NetUserMessageHandler<T> implements MessageHandler<T> {

    @Override
    public final void exec(ImContext ctx, NetUser netUser, T msg) {
        MessageLite res = handle(ctx, netUser, msg);
        if (res != null) {
            netUser.writeNow(ctx, res);
        }
    }

    public abstract MessageLite handle(ImContext ctx, NetUser netUser, T msg);

}
