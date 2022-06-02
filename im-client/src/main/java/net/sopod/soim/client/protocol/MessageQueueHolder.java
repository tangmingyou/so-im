package net.sopod.soim.client.protocol;

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
public class MessageQueueHolder {

    private static final Logger logger = LoggerFactory.getLogger(MessageQueueHolder.class);

    private static MessageQueueHolder INSTANCE;

    private final AtomicInteger serialNoGen;

    // TODO 超时处理
    private final ConcurrentHashMap<Integer, CompletableFuture<Object>> futureMap;

    private MessageQueueHolder() {
        this.serialNoGen = new AtomicInteger();
        this.futureMap = new ConcurrentHashMap<>();
    }

    public static MessageQueueHolder getInstance() {
        if (INSTANCE == null) {
            synchronized (MessageQueueHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MessageQueueHolder();
                }
            }
        }
        return INSTANCE;
    }

    public Pair<Integer, CompletableFuture<Object>> nextSerialNo() {
        int serialNo = serialNoGen.incrementAndGet();
        CompletableFuture<Object> future = new CompletableFuture<>();
        futureMap.put(serialNo, future);
        logger.info("put future: {}", serialNo);
        return Pair.of(serialNo, future);
    }

    public void futureComplete(int serialNo, MessageLite body) {
        CompletableFuture<Object> completableFuture = futureMap.remove(serialNo);
        if (completableFuture == null) {
            logger.error("无等待消息的future: {}, {}", serialNo, body);
        } else {
            completableFuture.complete(body);
        }
    }

}
