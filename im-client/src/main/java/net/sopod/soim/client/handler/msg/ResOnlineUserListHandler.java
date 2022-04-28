package net.sopod.soim.client.handler.msg;

import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.data.msg.user.UserGroup;

/**
 * ResOnlineUserListHandler
 *
 * @author tmy
 * @date 2022-04-28 12:52
 */
@Singleton
public class ResOnlineUserListHandler implements MessageHandler<UserGroup.ResOnlineUserList> {

    @Override
    public void handleMsg(UserGroup.ResOnlineUserList msg) {
        Logger.info("-------在线用户--------");
        for (UserGroup.UserInfo user : msg.getUsersList()) {
            Logger.info("{} | {}", user.getUid(), user.getAccount());
        }
    }

}
