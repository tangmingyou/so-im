package net.sopod.soim.router.datasync.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

/**
 * SyncClient
 *
 * @author tmy
 * @date 2022-05-05 15:03
 */
public class SyncClient {

    public SyncClient() {
        NioEventLoopGroup group = new NioEventLoopGroup(2);
        new Bootstrap()
                .group(group)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                            .addLast(new SyncLogDataCodec());
                    }
                });
    }


}
