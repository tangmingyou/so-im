package net.sopod.soim.core.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import net.sopod.soim.data.serialize.ImMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ImEntryCodec
 *
 * @author tmy
 * @date 2022-03-28 11:29
 */
public class ImEntryCodec extends CombinedChannelDuplexHandler<ImEntryCodec.ImDecoder, ImEntryCodec.ImEncoder> {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryCodec.class);

    public ImEntryCodec() {
        super(new ImDecoder(), new ImEncoder());
    }

    public static class ImDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
            ImMessage message = ImMessage.read(byteBuf);
            boolean isMagicError;
            if ((isMagicError = (message == ImMessage.MAGIC_ERROR))
                || message == ImMessage.PROTOCOL_ERROR) {
                logger.warn("decode im message error: {}, remote={}, closing channel.",
                        isMagicError ? "MagicError" : "ProtocolError",
                        ctx.channel().remoteAddress());
                ctx.channel().close();
                return;
            }
            list.add(message);
        }
    }

    public static class ImEncoder extends MessageToByteEncoder<ImMessage> {
        @Override
        protected void encode(ChannelHandlerContext ctx, ImMessage imMessage, ByteBuf byteBuf) throws Exception {
            imMessage.write(byteBuf);
        }
    }

}
