package net.sopod.soim.entry.server.handler;

import net.sopod.soim.entry.server.session.NetUser;

/**
 * MessageHandler
 *
 * @author tmy
 * @date 2022-04-10 23:40
 */
public interface MessageHandler<T> {

    void exec(NetUser netUser, T msg);

}
