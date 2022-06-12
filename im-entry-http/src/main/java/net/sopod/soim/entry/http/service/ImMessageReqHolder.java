package net.sopod.soim.entry.http.service;

import com.google.protobuf.MessageLite;
import io.netty.util.AttributeKey;
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
public class ImMessageReqHolder {

    private static final Logger logger = LoggerFactory.getLogger(ImMessageReqHolder.class);

    public static AttributeKey<ImMessageReqHolder> IM_MESSAGE_HOLDER_KEY = AttributeKey.valueOf(ImMessageReqHolder.class, "IM_MESSAGE_HOLDER_KEY");

    private final AtomicInteger serialNoGen;

    // TODO 超时处理
    private final ConcurrentHashMap<Integer, CompletableFuture<Object>> futureMap;

    public ImMessageReqHolder() {
        this.serialNoGen = new AtomicInteger();
        this.futureMap = new ConcurrentHashMap<>();
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
        CompletableFuture<Object> completableFuture = futureMap.remove(serialNo);
        if (completableFuture != null) {
            completableFuture.complete(msg);
        } else {
            // TODO dispatch im-entry 主动消息
            logger.warn("no future wait for msg: {}", msg);
        }
    }

}
