package net.sopod.soim.core.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import net.sopod.soim.data.serialize.ImMessage;

import java.util.List;

/**
 * ImEntryCodec
 *
 * @author tmy
 * @date 2022-03-28 11:29
 */
public class ImEntryCodec extends CombinedChannelDuplexHandler<ImEntryCodec.ImDecoder, ImEntryCodec.ImEncoder> {

    public ImEntryCodec() {
        super(new ImDecoder(), new ImEncoder());
    }

    public static class ImDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
            ImMessage message = ImMessage.read(byteBuf);
            if (message == ImMessage.MAGIC_ERROR
                || message == ImMessage.PROTOCOL_ERROR) {
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
