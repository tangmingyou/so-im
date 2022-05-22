package net.sopod.soim.logic.user.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.user.service.AccountService;
import net.sopod.soim.logic.common.model.UserInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * AccountServiceImpl
 *
 * @author tmy
 * @date 2022-05-21 19:08
 */
@DubboService
public class AccountServiceImpl implements AccountService {

    @DubboReference
    private UserDas userDas;

    @Override
    public CompletableFuture<List<UserInfo>> searchAccountLikely(String keyword) {
        // 查询用户账户列表
        List<ImUser> imUsers = userDas.searchImUser(keyword, 10);
        List<UserInfo> userInfos = imUsers.stream().map(imUser ->
                new UserInfo()
                        .setUid(imUser.getId())
                        .setAccount(imUser.getAccount())
                        .setNickname(imUser.getNickname())
        ).collect(Collectors.toList());
        return CompletableFuture.completedFuture(userInfos);
    }

}
