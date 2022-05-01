package net.sopod.soim.entry.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.handler.MessageHandler;
import net.sopod.soim.core.session.NetUser;

/**
 * NetUserMessageHandler
 *
 * @author tmy
 * @date 2022-04-10 23:40
 */
public abstract class NetUserMessageHandler<T> implements MessageHandler<T> {

    @Override
    public final void exec(NetUser netUser, T msg) {
        MessageLite res = handle(netUser, msg);
        if (res != null) {
            netUser.writeNow(res);
        }
    }

    public abstract MessageLite handle(NetUser netUser, T msg);

}
