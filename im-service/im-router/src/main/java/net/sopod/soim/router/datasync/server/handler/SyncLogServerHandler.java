package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.SyncTypes;
import net.sopod.soim.router.datasync.server.codec.CodecUtil;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;

import java.util.List;

/**
 * SyncLogServerHandler
 * 即将删除的节点：会主动推送同步数据，这里进行接收
 *
 * @author tmy
 * @date 2022-05-08 18:48
 */
public class SyncLogServerHandler extends SimpleChannelInboundHandler<SyncLog> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncLog syncLog) throws Exception {
        List<byte[]> bytesList = syncLog.getSerializeDataCollect();
        SyncTypes.SyncType<DataSync> syncType = SyncTypes.getSyncType(syncLog.getSyncType());
        for (byte[] bytes : bytesList) {
            DataSync instance = CodecUtil.decode(bytes, syncType.dataType());
            syncType.addData(instance);
            System.out.println(instance);
        }
        // 同步完成响应
        SyncCmd syncCmd = new SyncCmd().setCmdType(SyncCmd.SYNC_BY_HASH_ACK);
        ctx.writeAndFlush(syncCmd);
    }

}
