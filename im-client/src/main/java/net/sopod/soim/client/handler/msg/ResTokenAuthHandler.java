package net.sopod.soim.client.handler.msg;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.data.msg.auth.Auth;

/**
 * ResTokenAuthHandler
 *
 * @author tmy
 * @date 2022-04-27 9:46
 */
@Singleton
public class ResTokenAuthHandler implements MessageHandler<Auth.ResTokenAuth> {

    @Inject
    private SoImSession soImSession;

    @Override
    public void handleMsg(Auth.ResTokenAuth msg) {
        soImSession.authResult(msg.getSuccess(), msg.getMessage());
    }

}
