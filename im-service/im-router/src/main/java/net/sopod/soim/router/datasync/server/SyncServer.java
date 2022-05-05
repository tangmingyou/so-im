package net.sopod.soim.router.datasync.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SyncServer
 *
 * @author tmy
 * @date 2022-05-05 10:02
 */
public class SyncServer {

    private static final Logger logger = LoggerFactory.getLogger(SyncServer.class);

    public SyncServer() {
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        ServerBootstrap serverBoot = new ServerBootstrap()
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        LogLevel logLevel = logger.isDebugEnabled() ? LogLevel.DEBUG
                                : logger.isInfoEnabled() ? LogLevel.INFO
                                : logger.isWarnEnabled() ? LogLevel.WARN
                                : logger.isErrorEnabled() ? LogLevel.ERROR : LogLevel.INFO;
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new LoggingHandler(logLevel))
                                .addLast(new SyncLogDataCodec())
                                .addLast(new SyncLogInboundHandler());
                    }
                });
        serverBoot.bind(8080);
    }

    public void start() {

    }

}
