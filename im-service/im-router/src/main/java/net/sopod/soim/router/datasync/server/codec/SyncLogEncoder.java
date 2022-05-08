package net.sopod.soim.router.datasync.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;

/**
 * SyncDataInboundHandler
 * SyncLog 编码器
 *
 * @author tmy
 * @date 2022-05-05 10:20
 */
public class SyncLogEncoder extends MessageToByteEncoder<SyncLog> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, SyncLog syncLog, ByteBuf byteBuf) throws Exception {
        // 同步数据指令
        SyncCmd syncCmd = new SyncCmd()
                .setCmdType(SyncCmd.SYNC_LOG);
        byte[] syncCmdBytes = syncCmd.toByte();
        byteBuf.writeBytes(syncCmdBytes);

        // 同步数据内容
        byte[] bytes = syncLog.toBytes();
        // snappy 压缩一下
        byte[] compressLogBytes = CodecUtil.compress(bytes);
        byteBuf.writeInt(compressLogBytes.length);
        byteBuf.writeBytes(compressLogBytes);
    }

}
