package net.sopod.soim.router.datasync.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sopod.soim.common.util.netty.FastThreadLocalThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * SyncServer
 *
 * @author tmy
 * @date 2022-05-05 10:02
 */
public class SyncServer {

    private static final Logger logger = LoggerFactory.getLogger(SyncServer.class);

    private final int port;

    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;

    public SyncServer(int port) {
        this.port = port;
    }

    public void start(Consumer<Throwable> onFail) throws InterruptedException {
        this.boss = new NioEventLoopGroup(1, new FastThreadLocalThreadFactory("sync-server-boss-%d", Thread.NORM_PRIORITY));
        this.worker = new NioEventLoopGroup(2, new FastThreadLocalThreadFactory("sync-server-worker-%d", Thread.NORM_PRIORITY));
        ServerBootstrap serverBoot = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        LogLevel logLevel = logger.isDebugEnabled() ? LogLevel.DEBUG
                                : logger.isInfoEnabled() ? LogLevel.INFO
                                : logger.isWarnEnabled() ? LogLevel.WARN
                                : logger.isErrorEnabled() ? LogLevel.ERROR : LogLevel.INFO;
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LoggingHandler(logLevel))
                                .addLast(new SyncLogDataCodec())
                                 .addLast(new SyncServerInboundHandler());
                    }
                });
        serverBoot.bind(port).addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                if (onFail != null) {
                    onFail.accept(future.cause());
                }
                return;
            }
            logger.info("sync-server listening at {}...", port);
        });
    }

    public void shutdown() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

}
