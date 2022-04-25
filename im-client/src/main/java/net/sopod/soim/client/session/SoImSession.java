package net.sopod.soim.client.session;

import com.google.inject.Singleton;
import com.google.protobuf.MessageLite;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.core.net.ImMessageCodec;
import net.sopod.soim.data.msg.auth.Auth;
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

    public void connect(String host, Integer port, Auth.ReqTokenAuth tokenAuth) throws InterruptedException {
        eventLoopGroup = new NioEventLoopGroup(2);
        Bootstrap b = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new ImMessageCodec());
                        // .addLast(new ProtoMessageCodec());
                    }
                });
        clientChannel = b.connect(host, port).await().channel();

        // 连接后立即发送认证消息
        // TODO ResHandler
        clientChannel.write(tokenAuth);
    }

    public void send(MessageLite message) {
        if (!auth.get()) {
            Logger.error("请登录后发送消息");
            return;
        }
        if (clientChannel == null
            || !clientChannel.isActive()) {
            Logger.error("连接未打开");
            return;
        }
        clientChannel.write(message);
    }

    public void close() {
        if (clientChannel != null) {
            try {
                clientChannel.close().await();
            } catch (InterruptedException e) {
                logger.error("channel close error: ", e);
            }
        }
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

}
