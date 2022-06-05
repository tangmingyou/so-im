package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.util.netty.FastThreadLocalThreadFactory;
import net.sopod.soim.data.msg.user.Friend;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.entry.worker.FutureExecutor;
import net.sopod.soim.entry.worker.WorkerGroup;
import net.sopod.soim.logic.api.user.service.FriendService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public MessageLite handle(ImContext ctx, Account account, Friend.ReqAddFriend req) {
        // 好友id
        long fid = req.getFid();
        long uid = account.getUid();
        logger.info("添加好友: {}, {}", uid, fid);
        CompletableFuture<String> addFuture = friendService.addFriend(uid, fid);
        addFuture.whenCompleteAsync((msg, err) -> {
            if (err != null) {
                logger.error("添加好友失败:", err);
                return;
            }
            Friend.ResAddFriend.Builder builder = Friend.ResAddFriend.newBuilder();
            builder.setSuccess(msg == null);
            if (msg != null) {
                builder.setMsg(msg);
            }
            account.writeNow(ctx, builder.build());
            logger.info("添加好友成功");
        }, FutureExecutor.getInstance())
                .exceptionally(err -> {
                    logger.error("future执行失败: ", err);
                    return null;
                });
        return null;
    }

}
