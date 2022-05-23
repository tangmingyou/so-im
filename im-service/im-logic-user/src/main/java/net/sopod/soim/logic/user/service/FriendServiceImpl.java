package net.sopod.soim.logic.user.service;

import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.user.service.FriendService;
import net.sopod.soim.logic.common.model.UserInfo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * FriendServiceImpl
 *
 * @author tmy
 * @date 2022-05-23 15:44
 */
@DubboService
public class FriendServiceImpl implements FriendService {

    @DubboReference
    private FriendDas friendDas;

    @DubboReference
    private UserDas userDas;

    /**
     * TODO 幂等性处理
     */
    @Override
    public CompletableFuture<String> addFriend(Long uid, Long fid) {
        ImUser friend = userDas.getUserById(fid);
        CompletableFuture<String> future = new CompletableFuture<>();
        if (friend == null) {
            return CompletableFuture.completedFuture("不存在的账号");
        }
        if (Boolean.TRUE.equals(friendDas.isExists(uid, fid))) {
            return CompletableFuture.completedFuture("该用户已是你的好友");
        }
        // 添加好友数据
        friendDas.insert(uid, fid);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<List<UserInfo>> listFriend(Long uid) {
        List<ImUser> imUsers = friendDas.queryAllFriend(uid);
        List<UserInfo> userInfos = imUsers.stream().map(imUser -> new UserInfo()
                .setUid(imUser.getId())
                .setAccount(imUser.getAccount())
                .setNickname(imUser.getNickname())
        ).collect(Collectors.toList());
        return CompletableFuture.completedFuture(userInfos);
    }

}
