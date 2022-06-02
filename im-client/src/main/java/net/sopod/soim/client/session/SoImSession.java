package net.sopod.soim.client.session;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sopod.soim.client.config.exception.ClientException;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.protocol.ImMessageInboundHandler;
import net.sopod.soim.client.protocol.MessageQueueHolder;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.netty.Varint32FrameCodec;
import net.sopod.soim.data.proto.ProtoMessageManager;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;
import net.sopod.soim.data.msg.auth.Auth;
import net.sopod.soim.data.msg.chat.Chat;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * SoImSession
 *
 * @author tmy
 * @date 2022-04-25 22:49
 */
@Singleton
public class SoImSession {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SoImSession.class);

    private NioEventLoopGroup eventLoopGroup;

    private Channel clientChannel;

    private AtomicBoolean auth = new AtomicBoolean(false);

    private final MessageDispatcher messageDispatcher;

    private Long uid;

    private String account;

    @Inject
    public SoImSession(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    public void connect(String host, Integer port, Auth.ReqTokenAuth tokenAuth, String account) {
        // 关闭旧连接
        this.close();

        eventLoopGroup = new NioEventLoopGroup(2);
        Bootstrap b = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                            .addLast(new LoggingHandler(LogLevel.INFO))
                            .addLast(new Varint32FrameCodec())
//                            .addLast(new ImMessageCodec())
                            .addLast(new ImMessageCodec.ImMessage2ByteEncoder())
//                            .addLast(new ImMessageCodec.ImMessageDecoder())
                            .addLast(new ImMessageInboundHandler());
                            // .addLast(messageDispatcher);
                    }
                });
        try {
            Logger.info("连接中...");
            clientChannel = b.connect(host, port).await().channel();
            // 连接后立即发送认证消息，10s未认证连接关闭
            CompletableFuture<?> future = this.send0(tokenAuth);
            long start = ImClock.millis();
            Object res = future.join();
            logger.info("future 响应: {}, {}ms", res, ImClock.millis() - start);
            this.account = account;
        } catch (Exception e) {
            Logger.error("连接服务器失败: {}", e.getMessage());
        }
    }

    /**
     * 处理登录认证结果
     */
    public void authResult(boolean success, String message, Long uid) {
        this.auth.set(success);
        if (this.auth.get()) {
            Logger.info("连接服务器成功");
            this.uid = uid;
            return;
        }
        Logger.info("连接服务器失败: {}", message);
        this.close();
    }

    private CompletableFuture<?> send0(MessageLite message) {
        if (clientChannel == null
                || !clientChannel.isActive()) {
            throw new ClientException("连接已关闭");
        }
        // 构建 ImMessage
        Integer serialNo = ProtoMessageManager.getSerialNo(message.getClass());
        if (serialNo == null) {
            throw new ClientException("未知的消息类型:" + message.getClass());
        }
        ImMessage imMessage = new ImMessage()
                .setServiceNo(serialNo)
                .setBody(message.toByteArray());
        // serialNo, Future
        Pair<Integer, CompletableFuture<Object>> futurePair = MessageQueueHolder.getInstance().nextSerialNo();
        imMessage.setSerialNo(futurePair.getLeft());

        clientChannel.writeAndFlush(imMessage);
        return futurePair.getRight();
    }

    /**
     * 发送消息
     */
    @SuppressWarnings("unchecked")
    public <T> CompletableFuture<T> send(MessageLite message) {
        if (!auth.get()) {
            Logger.error("请先登录");
            return null;
        }
        return (CompletableFuture<T>) send0(message);
    }

    public void textChat(String receiverName, String message) {
        Chat.TextChat textChat = Chat.TextChat.newBuilder()
                .setMessage(message)
                .setReceiverAccount(receiverName)
                .setSender(uid)
                .build();
        send(textChat);
    }

    public void textChat(Chat.TextChat textChat) {
        this.send(textChat);
    }

    public Long getUid() {
        return uid;
    }

    public String getAccount() {
        return account;
    }

    /**
     * 关闭 tcp 连接
     */
    public void close() {
        if (clientChannel != null) {
            try {
                clientChannel.close().await();
                Logger.info("连接已关闭");
            } catch (InterruptedException e) {
                Logger.error("连接关闭失败: {}", e.getMessage());
            }
            this.clientChannel = null;
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
            this.eventLoopGroup = null;
        }
    }

}
