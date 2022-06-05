package net.sopod.soim.client.protocol;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.MessageLite;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * MessageQueueHolder
 * 发送一条消息产生一个序列号，在队列中等待响应消息
 *
 * @author tmy
 * @date 2022-06-02 17:29
 */
@Singleton
public class ImMessageReqHolder {

    private static final Logger logger = LoggerFactory.getLogger(ImMessageReqHolder.class);

    private final ImProtoDispatcher imProtoDispatcher;

    private final AtomicInteger serialNoGen;

    // TODO 超时处理
    private final ConcurrentHashMap<Integer, CompletableFuture<Object>> futureMap;

    @Inject
    public ImMessageReqHolder(ImProtoDispatcher imProtoDispatcher) {
        this.serialNoGen = new AtomicInteger();
        this.futureMap = new ConcurrentHashMap<>();
        this.imProtoDispatcher = imProtoDispatcher;
    }

    public Pair<Integer, CompletableFuture<Object>> nextSerialNo() {
        serialNoGen.compareAndSet(Integer.MAX_VALUE, 0);
        int serialNo = serialNoGen.incrementAndGet();
        CompletableFuture<Object> future = new CompletableFuture<>();
        futureMap.put(serialNo, future);
        logger.debug("put future: {}", serialNo);
        return Pair.of(serialNo, future);
    }

    /**
     * TODO complete fail
     */
    public void complete(int serialNo, MessageLite msg) {
        if (serialNo > 0) {
            // dispatch
            CompletableFuture<Object> completableFuture = futureMap.remove(serialNo);
            if (completableFuture != null) {
                completableFuture.complete(msg);
                return;
            }
        }
        // 无同步等待，通过消息分发处理
        imProtoDispatcher.dispatch(msg);
    }

}
