package net.sopod.soim.entry.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.sopod.soim.common.util.netty.Varint32FrameCodec;
import net.sopod.soim.core.net.ImMessageCodec;
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
    protected void initChannel(SocketChannel socketChannel) {
        logger.info("init channel: {}", socketChannel);
        LogLevel logLevel = logger.isDebugEnabled() ? LogLevel.DEBUG : LogLevel.INFO;
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LoggingHandler(logLevel))
                .addLast(new Varint32FrameCodec())
                .addLast(new ImMessageCodec())
                .addLast(new InboundImMessageHandler());
    }

}
