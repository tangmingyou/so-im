package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.data.msg.user.AccountSearch;
import net.sopod.soim.data.msg.user.Friend;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.user.service.FriendService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * ReqAddFriendHandler
 *
 * @author tmy
 * @date 2022-05-23 15:39
 */
@Service
public class ReqAddFriendHandler extends AccountMessageHandler<Friend.ReqAddFriend> {

    private static final Logger logger = LoggerFactory.getLogger(ReqAddFriendHandler.class);

    @DubboReference
    private FriendService friendService;

    @Override
    public MessageLite handle(Account account, Friend.ReqAddFriend req) {
        // 好友id
        long fid = req.getFid();
        long uid = account.getUid();
        logger.info("添加好友: {}, {}", uid, fid);
        CompletableFuture<String> addFuture = friendService.addFriend(uid, fid);
        addFuture.whenComplete((msg, err) -> {
            if (err != null) {
                logger.error("添加好友失败:", err);
                return;
            }
            Friend.ResAddFriend res = Friend.ResAddFriend.newBuilder()
                    .setSuccess(msg == null).setMsg(msg).build();
            account.writeNow(res);
            logger.info("添加好友成功");
        });
        return null;
    }

}
