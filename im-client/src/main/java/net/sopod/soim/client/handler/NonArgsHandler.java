package net.sopod.soim.client.handler;

/**
 * NoArgsHandler
 *
 * @author tmy
 * @date 2022-04-25 12:00
 */
public abstract class NonArgsHandler implements CmdHandler<Object> {

    @Override
    public final Object newArgsInstance() {
        return new Object();
    }

    @Override
    public final void handleArgs(Object args) {
        this.handle();
    }

    abstract void handle();

}
