package net.sopod.soim.entry.server.handler;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.dubbo.exception.SoimException;
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
    public final void exec(ImContext ctx, NetUser netUser, T req) {
        if (!netUser.isAccount()) {
            throw new SoimException("NetUser is not Account!" + netUser);
        }
        MessageLite res = handle(ctx, (Account) netUser, req);
        if (res != null) {
            netUser.writeNow(ctx, res);
        }
    }

    public abstract MessageLite handle(ImContext ctx, Account account, T req);

}
