package net.sopod.soim.entry.handlers.friend;

import com.google.protobuf.MessageLite;
import lombok.extern.slf4j.Slf4j;
import net.sopod.soim.data.msg.friend.Friend;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.friend.search.AccountInfo;
import net.sopod.soim.logic.api.friend.search.AccountSearchService;
import net.sopod.soim.logic.api.friend.search.ReqAccountSearch;
import net.sopod.soim.logic.api.friend.search.ResAccountSearch;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * ReqFriendSearchHandler
 * 账号搜索服务
 *
 * @author tmy
 * @date 2022-05-17 22:54
 */
@Slf4j
@Service
public class ReqFriendSearchHandler extends AccountMessageHandler<Friend.ReqFriendSearch> {

    @DubboReference
    private AccountSearchService accountSearchService;

    @Override
    public MessageLite handle(Account account, Friend.ReqFriendSearch msg) {
        msg.getKeyword();
        ReqAccountSearch search = ReqAccountSearch.newBuilder().setKeyword(msg.getKeyword()).build();
        CompletableFuture<ResAccountSearch> future = accountSearchService.searchAccountLikelyAsync(search);
        future.thenAccept(res -> {
            log.info("用户搜索开始...");
            List<AccountInfo> accounts = res.getAccountsList();
            List<Friend.UserInfo> userInfos = accounts.stream().map(
                    acc -> Friend.UserInfo.newBuilder()
                            .setAccount(acc.getAccount())
                            .setNickname(acc.getNickname())
                            .setUid(acc.getUid())
                            .build()
            ).collect(Collectors.toList());
            Friend.ResFriendSearch resSearch = Friend.ResFriendSearch.newBuilder().addAllUsers(userInfos).build();
            account.writeNow(resSearch);
        });
        return null;
    }

}
