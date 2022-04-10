package net.sopod.soim.core.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.session.NetUser;

/**
 * ProtoMessageHandler
 * <T extends MessageLite>
 *
 * @author tmy
 * @date 2022-04-10 19:19
 */
public abstract class ProtoMessageHandler<T> {

    public final void exec(NetUser netUser, T msg) {
        MessageLite res = handle(msg);
        if (res != null) {
            netUser.write(res);
        }
    }

    public abstract Class<T> type();

    public abstract MessageLite handle(T msg);

}
