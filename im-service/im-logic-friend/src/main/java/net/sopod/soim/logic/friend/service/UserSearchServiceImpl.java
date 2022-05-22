package net.sopod.soim.logic.friend.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.friend.search.AccountInfo;
import net.sopod.soim.logic.api.friend.search.AccountSearchService;
import net.sopod.soim.logic.api.friend.search.ReqAccountSearch;
import net.sopod.soim.logic.api.friend.search.ResAccountSearch;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * UserSearchServiceImpl
 *
 * @author tmy
 * @date 2022-05-15 22:43
 */
@DubboService
public class UserSearchServiceImpl implements AccountSearchService {

    @DubboReference
    private UserDas userDas;

    @Override
    public ResAccountSearch searchAccountLikely(ReqAccountSearch request) {
        // 查询用户账户列表
        List<ImUser> imUsers = userDas.searchImUser(request.getKeyword(), 10);
        List<AccountInfo> userInfos = imUsers.stream().map(
                imUser -> AccountInfo.newBuilder().
                        setAccount(imUser.getAccount())
                        .setNickname(imUser.getNickname())
                        .setUid(imUser.getId()).build()
        ).collect(Collectors.toList());

        return ResAccountSearch.newBuilder()
                .addAllAccounts(userInfos)
                .build();
    }

    @Override
    public CompletableFuture<ResAccountSearch> searchAccountLikelyAsync(ReqAccountSearch request) {
        return CompletableFuture.completedFuture(searchAccountLikely(request));
    }

}
