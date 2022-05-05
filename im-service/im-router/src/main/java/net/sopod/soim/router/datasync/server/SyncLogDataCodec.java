package net.sopod.soim.router.datasync.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * SyncDataInboundHandler
 *
 * @author tmy
 * @date 2022-05-05 10:20
 */
public class SyncLogDataCodec
        extends CombinedChannelDuplexHandler<SyncLogDataCodec.SyncDataDecoder,
        SyncLogDataCodec.SyncDataEncoder> {

    public SyncLogDataCodec() {
        super(new SyncDataDecoder(), new SyncDataEncoder());
    }

    /**
     * 解码器
     */
    public static class SyncDataDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            SyncLog syncLog = SyncLog.read(byteBuf);
            list.add(syncLog);
        }

    }

    /**
     * 编码器
     */
    public static class SyncDataEncoder extends MessageToByteEncoder<SyncLog> {

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, SyncLog syncLog, ByteBuf byteBuf) throws Exception {
            byte[] bytes = syncLog.toBytes();
            byteBuf.writeBytes(bytes);
        }

    }

}
