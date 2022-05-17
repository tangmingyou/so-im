package net.sopod.soim.entry.server.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.entry.server.session.NetUser;

/**
 * AccountMessageHandler
 * 每次调用服务前
 *
 * @author tmy
 * @date 2022-04-10 23:41
 */
public abstract class AccountMessageHandler<T> implements MessageHandler<T> {

    @Override
    public final void exec(NetUser netUser, T msg) {
        if (!netUser.isAccount()) {
            throw new IllegalStateException("NetUser is not account!" + netUser);
        }
        MessageLite res = handle((Account) netUser, msg);
        if (res != null) {
            netUser.writeNow(res);
        }
    }

    public abstract MessageLite handle(Account account, T msg);

}