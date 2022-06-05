package net.sopod.soim.client.handler.cmd;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.sopod.soim.client.cmd.args.ArgsAddFriend;
import net.sopod.soim.client.cmd.handler.CmdHandler;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.session.SoImSession;
import net.sopod.soim.data.msg.user.Friend;

import java.util.concurrent.CompletableFuture;

/**
 * AddFriendHandler
 *
 * @author tmy
 * @date 2022-05-21 19:29
 */
@Singleton
public class AddFriendHandler implements CmdHandler<ArgsAddFriend> {

    @Inject
    private SoImSession soImSession;

    @Override
    public ArgsAddFriend newArgsInstance() {
        return new ArgsAddFriend();
    }

    @Override
    public void handleArgs(ArgsAddFriend args) {
        Friend.ReqAddFriend req = Friend.ReqAddFriend.newBuilder()
                .setFid(args.getFriendId())
                .build();
        CompletableFuture<Friend.ResAddFriend> future = soImSession.send(req);
        Friend.ResAddFriend res = future.join();
        if (res.getSuccess()) {
            Console.info("添加好友成功");
        } else {
            Console.error("添加失败:{}", res.getMsg());
        }
    }

}
