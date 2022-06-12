package net.sopod.soim.client.session;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sopod.soim.client.config.exception.ClientException;
import net.sopod.soim.client.logger.Console;
import net.sopod.soim.client.protocol.ImMessageInboundHandler;
import net.sopod.soim.client.protocol.ImMessageReqHolder;
import net.sopod.soim.common.util.netty.Varint32FrameCodec;
import net.sopod.soim.data.msg.auth.Auth;
import net.sopod.soim.data.serialize.ImMessage;
import net.sopod.soim.data.serialize.ImMessageCodec;
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

    @Inject
    private ImMessageReqHolder imMessageReqHolder;

    @Inject
    private ImMessageInboundHandler imMessageInboundHandler;

    private NioEventLoopGroup eventLoopGroup;

    private Channel clientChannel;

    private AtomicBoolean auth = new AtomicBoolean(false);

    private Long uid;

    private String account;

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
                            // .addLast(new LoggingHandler(LogLevel.INFO))
                            .addLast(new Varint32FrameCodec())
                            .addLast(new ImMessageCodec.ImMessage2ByteEncoder())
                            .addLast(imMessageInboundHandler);
                    }
                })
                .option(ChannelOption.TCP_NODELAY, true);
        try {
            Console.info("连接中...");
            clientChannel = b.connect(host, port).await().channel();
            // 连接后立即发送认证消息，10s未认证连接关闭
            CompletableFuture<?> future = this.send0(tokenAuth);
            Auth.ResTokenAuth res = (Auth.ResTokenAuth) future.join();
            if (res.getSuccess()) {
                this.account = account;
                this.auth.set(true);
                this.uid = res.getUid();
                Console.info("连接服务器成功");
            } else {
                Console.info("连接服务器失败 {}", res.getMessage());
            }
            // logger.info("future 响应: {}, {}ms", res, ImClock.millis() - start);
        } catch (Exception e) {
            Console.error("连接服务器失败: {}", e.getMessage());
        }
    }

    private CompletableFuture<?> send0(MessageLite message) {
        if (clientChannel == null
                || !clientChannel.isActive()) {
            throw new ClientException("连接已关闭");
        }
        // 构建 ImMessage
        ImMessage imMessage = ImMessageCodec.encodeImProto(message);
        // serialNo, Future
        Pair<Integer, CompletableFuture<Object>> futurePair = imMessageReqHolder.nextSerialNo();
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
            Console.error("请先登录");
            return null;
        }
        return (CompletableFuture<T>) send0(message);
    }

    public void justSend(MessageLite message) {
        if (!auth.get()) {
            Console.error("请先登录");
            return;
        }
        if (clientChannel == null
                || !clientChannel.isActive()) {
            // TODO 尝试重连
            throw new ClientException("连接已关闭");
        }
        ImMessage imMessage = ImMessageCodec.encodeImProto(message);
        imMessage.setSerialNo(-1);
        clientChannel.writeAndFlush(imMessage);
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
                Console.info("连接已关闭");
            } catch (InterruptedException e) {
                Console.error("连接关闭失败: {}", e.getMessage());
            }
            this.clientChannel = null;
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
            this.eventLoopGroup = null;
        }
    }

}
