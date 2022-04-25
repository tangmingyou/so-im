package net.sopod.soim.client.handler;

import com.google.inject.Singleton;

/**
 * ExitHandler
 *
 * @author tmy
 * @date 2022-04-25 11:53
 */
@Singleton
public class ExitHandler extends NonArgsHandler {

    @Override
    public void handle() {
        System.exit(1);
    }

}
