package net.sopod.soim.client.session;

/**
 * MessageHandler
 *
 * @author tmy
 * @date 2022-04-27 9:50
 */
public interface MessageHandler<T> {

    void handleMsg(T res);

}
