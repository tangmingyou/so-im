package net.sopod.soim.logic.api.user.service;

import net.sopod.soim.logic.common.model.UserInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * FriendService
 *
 * @author tmy
 * @date 2022-05-23 15:42
 */
public interface FriendService {

    /**
     * 添加好友
     * @return 成功返回 null,失败返回失败原因
     */
    CompletableFuture<String> addFriend(Long uid, Long fid);

    /**
     * 获取好友列表
     */
    CompletableFuture<List<UserInfo>> listFriend(Long uid);

}
