package net.sopod.soim.logic.friend.service;

import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.friend.search.UserSearchReply;
import net.sopod.soim.logic.api.friend.search.UserSearchReq;
import net.sopod.soim.logic.api.friend.search.UserSearchService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.CompletableFuture;

/**
 * UserSearchServiceImpl
 *
 * @author tmy
 * @date 2022-05-15 22:43
 */
@DubboService
public class UserSearchServiceImpl implements UserSearchService {

    @DubboReference
    private UserDas userDas;

    @Override
    public UserSearchReply searchUser(UserSearchReq request) {
        return null;
    }

    @Override
    public CompletableFuture<UserSearchReply> searchUserAsync(UserSearchReq request) {
        return CompletableFuture.completedFuture(searchUser(request));
    }

}
