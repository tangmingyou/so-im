package net.sopod.soim.client.cmd.handler;

/**
 * CmdHandler
 *
 * @author tmy
 * @date 2022-04-25 10:56
 */
public interface CmdHandler<T> {

    T newArgsInstance();

    void handleArgs(T args);

}
