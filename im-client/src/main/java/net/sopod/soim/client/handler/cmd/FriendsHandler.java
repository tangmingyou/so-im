package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.handler.NonArgsHandler;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.data.msg.user.Friend;

/**
 * FriendsHandler
 *
 * @author tmy
 * @date 2022-05-23 22:58
 */
@Singleton
public class FriendsHandler extends NonArgsHandler {

    @Inject
    private SoImSession soImSession;

    @Override
    public void handle() {
        Friend.ReqFriendList req = Friend.ReqFriendList.newBuilder().build();
        soImSession.send(req);
    }

}
