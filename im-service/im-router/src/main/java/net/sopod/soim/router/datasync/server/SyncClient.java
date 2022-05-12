package net.sopod.soim.router.datasync.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import net.sopod.soim.common.util.netty.Varint32FrameCodec;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.datasync.SyncTypes;
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
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {

                        channel.pipeline()
                                .addLast(new LoggingHandler(LogLevel.INFO))
                                // 接收字节可能会分段到达，添加帧编解码器
                                .addLast(new Varint32FrameCodec())
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

    /**
     * 通过计算一致性 hash 同步数据
     * @param currentAddr 当前新节点地址，hash(currentAddr)
     */
    public void syncLogByHash(String currentAddr) {
        SyncCmd syncByHash = SyncCmd.syncByHash(currentAddr);
        clientChannel.writeAndFlush(syncByHash);
    }

    public void close() {
        this.clientChannel.close();
        this.group.shutdownGracefully();
    }

}
