    package net.sopod.soim.logic.api.friend.search;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: proto/AccountSearch.proto")
public final class AccountSearchServiceDubbo {
private static final AtomicBoolean registered = new AtomicBoolean();

public static boolean init() {
    if (registered.compareAndSet(false, true)) {
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            net.sopod.soim.logic.api.friend.search.ReqAccountSearch.getDefaultInstance());
            org.apache.dubbo.common.serialize.protobuf.support.ProtobufUtils.marshaller(
            net.sopod.soim.logic.api.friend.search.ResAccountSearch.getDefaultInstance());
    }
    return true;
}

private AccountSearchServiceDubbo() {}

}
