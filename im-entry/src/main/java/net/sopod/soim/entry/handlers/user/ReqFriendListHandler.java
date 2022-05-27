package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import lombok.extern.slf4j.Slf4j;
import net.sopod.soim.data.msg.user.Friend;
import net.sopod.soim.data.msg.user.UserMsg;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.entry.worker.FutureExecutor;
import net.sopod.soim.logic.api.user.service.FriendService;
import net.sopod.soim.logic.common.model.UserInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * ReqFriendListHandler
 *
 * @author tmy
 * @date 2022-05-23 17:42
 */
@Slf4j
@Service
public class ReqFriendListHandler extends AccountMessageHandler<Friend.ReqFriendList> {

    @DubboReference
    private FriendService friendService;

    @Override
    public MessageLite handle(Account account, Friend.ReqFriendList req) {
        CompletableFuture<List<UserInfo>> future = friendService.listFriend(account.getUid());
        future.whenCompleteAsync((friends, err) -> {
            List<UserMsg.UserInfo> userInfos = friends.stream().map(acc -> UserMsg.UserInfo.newBuilder()
                    .setAccount(acc.getAccount())
                    .setNickname(acc.getNickname())
                    .setUid(acc.getUid())
                    .setOnline(Boolean.TRUE.equals(acc.getIsOnline()))
                    .build()
            ).collect(Collectors.toList());
            Friend.ResFriendList res = Friend.ResFriendList.newBuilder().addAllFriends(userInfos).build();
            account.writeNow(res);
        }, FutureExecutor.getInstance())
                .exceptionally(err -> {
                    log.error("好友列表查询失败", err);
                    return Collections.emptyList();
                });
        return null;
    }

}
