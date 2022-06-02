package net.sopod.soim.data.serialize;

import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.sopod.soim.data.proto.ProtoMessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * ImEntryCodec
 *
 * @author tmy
 * @date 2022-03-28 11:29
 */
public class ImMessageCodec //extends MessageToMessageCodec<ByteBuf, MessageLite> {
        extends CombinedChannelDuplexHandler<ImMessageCodec.ProtoMsgDecoder, ImMessageCodec.ProtoMsgEncoder> {

    private static final Logger logger = LoggerFactory.getLogger(ImMessageCodec.class);

    public ImMessageCodec() {
         super(new ProtoMsgDecoder(), new ProtoMsgEncoder());
    }

    public static class ImMessageDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
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
            // 解码 protobuf 消息体
            int serviceNo = message.getServiceNo();
            byte[] protoByte = message.getBody();
            MessageLite protoClass = ProtoMessageManager.getProtoInstance(serviceNo);
            MessageLite protoMsg = protoClass.getParserForType().parseFrom(protoByte);
            message.setDecodeBody(protoMsg);
            out.add(message);
        }
    }

    public static class ProtoMsgDecoder extends ByteToMessageDecoder {
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
            // 解码 protobuf 消息体
            int serviceNo = message.getServiceNo();
            // message.getSerialNo()
            byte[] protoByte = message.getBody();
            MessageLite protoClass = ProtoMessageManager.getProtoInstance(serviceNo);
            MessageLite protoMsg = protoClass.getParserForType().parseFrom(protoByte);
            list.add(protoMsg);
        }
    }

    public static class ProtoMsg2ImMessageEncoder extends MessageToMessageEncoder<MessageLite> {
        @Override
        protected void encode(ChannelHandlerContext ctx, MessageLite message, List<Object> out) throws Exception {
            Integer serialNo = ProtoMessageManager.getSerialNo(message.getClass());
            // TODO unknow class serialNo
            ImMessage imMessage = new ImMessage()
                    .setServiceNo(serialNo)
                    .setBody(message.toByteArray());
            out.add(imMessage);
        }
    }

    public static class ImMessage2ByteEncoder extends MessageToByteEncoder<ImMessage> {
        @Override
        protected void encode(ChannelHandlerContext ctx, ImMessage imMessage, ByteBuf out) throws Exception {
            imMessage.write(out);
        }
    }

    public static class ProtoMsgEncoder extends MessageToByteEncoder<MessageLite> {
        @Override
        protected void encode(ChannelHandlerContext ctx, MessageLite message, ByteBuf byteBuf) throws Exception {
            Integer serialNo = ProtoMessageManager.getSerialNo(message.getClass());
            // TODO unknow class serialNo
            ImMessage imMessage = new ImMessage()
                    .setServiceNo(serialNo)
                    .setBody(message.toByteArray());
            imMessage.write(byteBuf);
        }
    }

}
