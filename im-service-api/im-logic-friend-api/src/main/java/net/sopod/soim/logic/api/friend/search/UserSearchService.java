    package net.sopod.soim.logic.api.friend.search;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: proto/UserSearch.proto")
public interface UserSearchService {
static final String JAVA_SERVICE_NAME = "net.sopod.soim.logic.api.friend.search.UserSearchService";
static final String SERVICE_NAME = "net.sopod.soim.logic.api.friend.search.UserSearchService";

    // FIXME, initialize Dubbo3 stub when interface loaded, thinking of new ways doing this.
    static final boolean inited = UserSearchServiceDubbo.init();

    net.sopod.soim.logic.api.friend.search.UserSearchReply searchUser(net.sopod.soim.logic.api.friend.search.UserSearchReq request);

    CompletableFuture<net.sopod.soim.logic.api.friend.search.UserSearchReply> searchUserAsync(net.sopod.soim.logic.api.friend.search.UserSearchReq request);


}
