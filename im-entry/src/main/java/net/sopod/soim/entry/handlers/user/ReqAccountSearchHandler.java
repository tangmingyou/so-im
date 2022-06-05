package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.data.msg.user.AccountSearch;
import net.sopod.soim.data.msg.user.UserGroup;
import net.sopod.soim.data.msg.user.UserMsg;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.user.service.AccountService;
import net.sopod.soim.logic.common.model.UserInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * ReqAccountSearchHandler
 *
 * @author tmy
 * @date 2022-05-21 19:41
 */
@Service
public class ReqAccountSearchHandler extends AccountMessageHandler<AccountSearch.ReqAccountSearch> {

    @DubboReference
    private AccountService accountService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, AccountSearch.ReqAccountSearch msg) {
        CompletableFuture<List<UserInfo>> future = accountService.searchAccountLikely(msg.getKeyword());
        future.thenAccept(userInfos -> {
            List<UserMsg.UserInfo> infos = userInfos.stream().map(
                    acc -> UserMsg.UserInfo.newBuilder()
                            .setAccount(acc.getAccount())
                            .setNickname(acc.getNickname())
                            .setUid(acc.getUid())
                            .build()
            ).collect(Collectors.toList());
            AccountSearch.ResAccountSearch resSearch = AccountSearch.ResAccountSearch
                    .newBuilder()
                    .addAllUsers(infos).build();
            account.writeNow(ctx, resSearch);
        });
        return null;
    }

}
