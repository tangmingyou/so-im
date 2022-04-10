package net.sopod.soim.core.handler;

import net.sopod.soim.core.session.NetUser;

/**
 * MessageHandler
 *
 * @author tmy
 * @date 2022-04-10 23:40
 */
public interface MessageHandler<T> {

    void exec(NetUser netUser, T msg);

}
