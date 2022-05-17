    package net.sopod.soim.router.api.service.storage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@javax.annotation.Generated(
value = "by Dubbo generator",
comments = "Source: proto/RouterStorage.proto")
public final class RouterStorageServiceDubbo {
private static final AtomicBoolean registered = new AtomicBoolean();

public static boolean init() {
    if (registered.compareAndSet(false, true)) {
    }
    return true;
}

private RouterStorageServiceDubbo() {}

}
