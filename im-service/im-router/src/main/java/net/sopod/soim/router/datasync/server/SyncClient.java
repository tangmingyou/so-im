package net.sopod.soim.router.datasync.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
import net.sopod.soim.router.datasync.server.codec.SyncCmdCodec;
import net.sopod.soim.router.datasync.server.codec.SyncLogEncoder;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;
import net.sopod.soim.router.datasync.server.handler.SyncCmdClientHandler;
import net.sopod.soim.router.datasync.server.handler.SyncLogClientHandler;

/**
 * SyncClient
 *
 * @author tmy
 * @date 2022-05-05 15:03
 */
public class SyncClient {

    private NioEventLoopGroup group;

    private final Bootstrap bootstrap;

    private Channel clientChannel;

    public SyncClient() {
        this.bootstrap = new Bootstrap()
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new SyncCmdCodec())
                                .addLast(new SyncLogEncoder())
                                .addLast(new SyncCmdClientHandler())
                                .addLast(new SyncLogClientHandler());
                    }
                });
    }

    public void connect(String host, int port) throws InterruptedException {
        this.group = new NioEventLoopGroup(2);
        this.clientChannel = bootstrap
                .group(group)
                .connect(host, port)
                .await().channel();
    }

    public void write(SyncCmd syncCmd) {
        clientChannel.writeAndFlush(syncCmd);
    }

    public void write(SyncLog syncLog) {
        clientChannel.write(syncLog);
    }

    public <T> void setAttr(AttributeKey<T> key, T val) {
        clientChannel.attr(key).set(val);
    }

    public <T> void getAttr(AttributeKey<T> key) {
        clientChannel.attr(key).get();
    }

    public void close() {
        this.group.shutdownGracefully();
    }

}
