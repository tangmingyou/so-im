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
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.common.util.netty.Varint32FrameCodec;
import net.sopod.soim.data.serialize.ImMessageCodec;
import net.sopod.soim.data.msg.auth.Auth;
import net.sopod.soim.data.msg.chat.Chat;
import org.slf4j.LoggerFactory;

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

    @Inject
    public SoImSession(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    public void connect(String host, Integer port, Auth.ReqTokenAuth tokenAuth) {
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
                            .addLast(new Varint32FrameCodec())
                            .addLast(new ImMessageCodec())
                            .addLast(messageDispatcher);
                    }
                });
        try {
            Logger.info("连接中...");
            clientChannel = b.connect(host, port).await().channel();
            // 连接后立即发送认证消息，10s未认证连接关闭
            clientChannel.writeAndFlush(tokenAuth);
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

    /**
     * 发送消息
     */
    public void send(MessageLite message) {
        if (!auth.get()) {
            Logger.error("请先登录");
            return;
        }
        if (clientChannel == null
            || !clientChannel.isActive()) {
            Logger.error("连接已关闭");
            return;
        }
        clientChannel.writeAndFlush(message);
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
