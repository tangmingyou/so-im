package net.sopod.soim.client.handler.msg;

import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.data.msg.user.Friend;

/**
 * ResAddFriendHandler
 *
 * @author tmy
 * @date 2022-05-23 21:42
 */
@Singleton
public class ResAddFriendHandler implements MessageHandler<Friend.ResAddFriend> {

    @Override
    public void handleMsg(Friend.ResAddFriend res) {
        System.out.println(res);
        if (res.getSuccess()) {
            Logger.info("添加好友成功");
        } else {
            Logger.error("添加失败:{}", res.getMsg());
        }
    }

}
