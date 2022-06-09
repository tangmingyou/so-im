package net.sopod.soim.entry.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.sopod.soim.common.constant.Consts;
import net.sopod.soim.common.util.netty.FastThreadLocalThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * EntryServer
 *
 * @author tmy
 * @date 2022-03-28 11:19
 */
public class EntryServer {

    private static final Logger logger = LoggerFactory.getLogger(EntryServer.class);

    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;
    private ServerBootstrap serverBootstrap;

    private String name;
    private int port;

    public static final int LOW_WATER_MARK = 32 * Consts.KB;
    public static final int HIGH_WATER_MARK = 64 * Consts.KB;

    public EntryServer(String name, int port) {
        this.name = name;
        this.port = port;
        this.boss =
                new NioEventLoopGroup(1, new FastThreadLocalThreadFactory("entry-b-%d", Thread.MAX_PRIORITY));
        this.worker =
                new NioEventLoopGroup(new FastThreadLocalThreadFactory("entry-w-%d", Thread.MAX_PRIORITY));
        this.bootstrap();
    }

    private void bootstrap() {
        this.serverBootstrap = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ImEntryInitializer())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_RCVBUF, 32 * Consts.KB)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(LOW_WATER_MARK, HIGH_WATER_MARK))
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_RCVBUF, 32 * Consts.KB)
                .childOption(ChannelOption.SO_SNDBUF, 64 * Consts.KB)
                .childOption(ChannelOption.SO_REUSEADDR, true)
                .childOption(
                        ChannelOption.WRITE_BUFFER_WATER_MARK,
                        new WriteBufferWaterMark(LOW_WATER_MARK, HIGH_WATER_MARK));
    }

    public void startServer(Consumer<Throwable> onFail) {
        this.serverBootstrap
                .bind(port)
                .addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        if (onFail != null) {
                            onFail.accept(future.cause());
                        }
                        return;
                    }
                    logger.info("{} listening at {}...", name, port);
                });
    }

    public void shutdown() {
        logger.info("netty reactor group shutting down...");
        boss.shutdownGracefully();
        worker.shutdownGracefully();
        logger.info("netty reactor group already shutdown!");
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

}
