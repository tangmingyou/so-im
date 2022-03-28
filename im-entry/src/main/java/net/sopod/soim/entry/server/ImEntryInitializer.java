package net.sopod.soim.entry.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sopod.soim.core.net.ImEntryCodec;
import net.sopod.soim.core.net.ImMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ImEntryInitializer
 *
 * @author tmy
 * @date 2022-03-28 11:25
 */
public class ImEntryInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryInitializer.class);

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        LogLevel logLevel = logger.isDebugEnabled() ? LogLevel.DEBUG : LogLevel.INFO;
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LoggingHandler(logLevel))
                .addLast(new ImEntryCodec())
                .addLast(new ImMessageHandler());
    }

}
