package net.sopod.soim.client.handler.msg;

import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.data.msg.user.Friend;
import net.sopod.soim.data.msg.user.UserMsg;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ResFriendListHandler
 *
 * @author tmy
 * @date 2022-05-23 22:59
 */
@Singleton
public class ResFriendListHandler implements MessageHandler<Friend.ResFriendList> {

    @Override
    public void handleMsg(Friend.ResFriendList res) {
        List<UserMsg.UserInfo> friendsList = res.getFriendsList();
        List<String> userLines = friendsList.stream()
                .map(u -> u.getUid() + "|" + u.getAccount() + "|" + u.getNickname() + "|" + u.getOnline())
                .collect(Collectors.toList());
        Logger.logList("好友列表", userLines);
    }

}
