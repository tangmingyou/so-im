package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.handler.NonArgsHandler;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.data.msg.user.Friend;
import net.sopod.soim.data.msg.user.UserMsg;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
        CompletableFuture<Friend.ResFriendList> future = soImSession.send(req);

        Friend.ResFriendList res = future.join();
        List<UserMsg.UserInfo> friendsList = res.getFriendsList();
        List<String> userLines = friendsList.stream()
                .map(u -> u.getUid() + "|" + u.getAccount() + "|" + u.getNickname() + "|" + u.getOnline())
                .collect(Collectors.toList());
        Console.logList("好友列表", userLines);
    }

}
