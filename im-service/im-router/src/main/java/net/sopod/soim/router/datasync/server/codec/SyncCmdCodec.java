package net.sopod.soim.router.datasync.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.CombinedChannelDuplexHandler;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;

import java.util.List;

/**
 * SyncChannelHandler
 * SyncCmd 编解码器
 *
 * @author tmy
 * @date 2022-05-07 21:37
 */
public class SyncCmdCodec extends CombinedChannelDuplexHandler<SyncCmdCodec.SyncCmdDecoder,
        SyncCmdCodec.SyncCmdEncoder> {

    public SyncCmdCodec() {
        super(new SyncCmdDecoder(), new SyncCmdEncoder());
    }

    public static class SyncCmdDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            SyncCmd syncCmd = SyncCmd.read(byteBuf);
            if (!SyncCmd.SYNC_LOG.equals(syncCmd.getCmdType())) {
                list.add(syncCmd);
                return;
            }
            // 数据同步请求命令
            // 同步数据请求体长度
            int syncLogBytesLen = byteBuf.readInt();
            // 读取、解压、解析字节数据
            byte[] syncLogCompressBytes = new byte[syncLogBytesLen];
            byteBuf.readBytes(syncLogCompressBytes);
            byte[] syncLogBytes = CodecUtil.uncompress(syncLogCompressBytes);
            ByteBuf syncLogByteBuf = Unpooled.wrappedBuffer(syncLogBytes);
            try {
                SyncLog syncLog = SyncLog.read(syncLogByteBuf);
                list.add(syncLog);
            } finally {
                syncLogByteBuf.release();
            }
        }
    }

    public static class SyncCmdEncoder extends MessageToByteEncoder<SyncCmd> {
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, SyncCmd syncCmd, ByteBuf byteBuf) throws Exception {
            byte[] bytes = syncCmd.toByte();
            byteBuf.writeBytes(bytes);
        }
    }

}
