    package net.sopod.soim.logic.api.friend.search;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: proto/AccountSearch.proto")
public interface AccountSearchService {
static final String JAVA_SERVICE_NAME = "net.sopod.soim.logic.api.friend.search.AccountSearchService";
static final String SERVICE_NAME = "net.sopod.soim.logic.api.friend.search.AccountSearchService";

    // FIXME, initialize Dubbo3 stub when interface loaded, thinking of new ways doing this.
    static final boolean inited = AccountSearchServiceDubbo.init();

    net.sopod.soim.logic.api.friend.search.ResAccountSearch searchAccountLikely(net.sopod.soim.logic.api.friend.search.ReqAccountSearch request);

    CompletableFuture<net.sopod.soim.logic.api.friend.search.ResAccountSearch> searchAccountLikelyAsync(net.sopod.soim.logic.api.friend.search.ReqAccountSearch request);


}
