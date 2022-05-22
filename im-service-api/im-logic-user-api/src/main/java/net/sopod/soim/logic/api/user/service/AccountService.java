package net.sopod.soim.logic.api.user.service;

import net.sopod.soim.logic.common.model.UserInfo;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * AccountService
 *
 * @author tmy
 * @date 2022-05-21 19:07
 */
public interface AccountService {

    CompletableFuture<List<UserInfo>> searchAccountLikely(String keyword);

}
