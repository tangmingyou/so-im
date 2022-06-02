package net.sopod.soim.data.serialize;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import net.sopod.soim.common.dubbo.exception.ConvertException;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.data.proto.ProtoMessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ImEntryCodec
 *
 * @author tmy
 * @date 2022-03-28 11:29
 */
public class ImMessageCodec {

    private static final Logger logger = LoggerFactory.getLogger(ImMessageCodec.class);

    public static class ImMessageDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
            ImMessage imMessage = ImMessageCodec.decodeImMessage(byteBuf);
            out.add(imMessage);
        }
    }

    public static class ImMessage2ByteEncoder extends MessageToByteEncoder<ImMessage> {
        @Override
        protected void encode(ChannelHandlerContext ctx, ImMessage imMessage, ByteBuf byteBuf) throws Exception {
            imMessage.write(byteBuf);
        }
    }

    public static ImMessage decodeImMessage(ByteBuf byteBuf) {
        ImMessage imMessage = ImMessage.read(byteBuf);
        boolean isMagicError;
        if ((isMagicError = (imMessage == ImMessage.MAGIC_ERROR))
                || imMessage == ImMessage.PROTOCOL_ERROR) {
            throw new ConvertException("ImMessage解码错误");
        }
        // 解码 protobuf 消息体
        int serviceNo = imMessage.getServiceNo();
        byte[] protoByte = imMessage.getBody();
        MessageLite protoClass = ProtoMessageManager.getDefaultInstance(serviceNo);
        if (protoClass == null) {
            throw new SoimException(String.format("不支持的消息编号: %d", serviceNo));
        }
        MessageLite protoMsg;
        try {
            protoMsg = protoClass.getParserForType().parseFrom(protoByte);
        } catch (InvalidProtocolBufferException e) {
            throw new ConvertException("ImMessage消息体Protobuf解码错误: " + e.getMessage());
        }
        imMessage.setDecodeBody(protoMsg);
        return imMessage;
    }

    public static ImMessage encodeImProto(MessageLite message) {
        Integer serialNo = ProtoMessageManager.getSerialNo(message.getClass());
        if (serialNo == null) {
            throw new SoimException("未知的Protobuf消息类型:" + message.getClass());
        }
        return new ImMessage()
                .setServiceNo(serialNo)
                .setBody(message.toByteArray());
    }

}
