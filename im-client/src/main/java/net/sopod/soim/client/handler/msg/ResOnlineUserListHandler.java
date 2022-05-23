package net.sopod.soim.client.handler.msg;

import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.data.msg.user.UserGroup;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ResOnlineUserListHandler
 *
 * @author tmy
 * @date 2022-04-28 12:52
 */
@Singleton
public class ResOnlineUserListHandler implements MessageHandler<UserGroup.ResOnlineUserList> {

    @Override
    public void handleMsg(UserGroup.ResOnlineUserList res) {
        List<String> userLines = res.getUsersList().stream()
                .map(user -> user.getUid() + " | " + user.getAccount())
                .collect(Collectors.toList());
        Logger.logList("在线用户", userLines);
    }

}
