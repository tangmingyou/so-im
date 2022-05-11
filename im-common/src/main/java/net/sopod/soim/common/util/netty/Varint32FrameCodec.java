package net.sopod.soim.common.util.netty;

import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * 帧编解码器
 * 接收字节可能会分段到达，添加帧编解码器，修改应用程序代码以不调用 readVarInt 和 writeVarInt
 * 接收到的 ByteBuf 的大小将始终与发送的 ByteBuf 的大小匹配，因为解码器负责聚合任何片段
 * https://netty.io/4.0/xref/io/netty/example/worldclock/WorldClockServerInitializer.html
 *
 * {@link ProtobufVarint32FrameDecoder} 它确保管道中的下一个处理程序一次总是接收准确的一帧字节
 * {@link ProtobufVarint32LengthFieldPrepender} 负责将帧长度 header 添加到所有传出消息
 *
 * @author tmy
 * @date 2022-05-11 17:06
 */
public class Varint32FrameCodec extends CombinedChannelDuplexHandler<
        ProtobufVarint32FrameDecoder,
        ProtobufVarint32LengthFieldPrepender> {

    public Varint32FrameCodec() {
        // FrameDecoder, LengthFieldPrepender
        super(new ProtobufVarint32FrameDecoder(), new ProtobufVarint32LengthFieldPrepender());
    }

}
