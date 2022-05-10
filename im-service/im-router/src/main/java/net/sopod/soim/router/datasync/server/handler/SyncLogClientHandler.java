package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.DataSyncStorage;
import net.sopod.soim.router.datasync.SyncTypes;
import net.sopod.soim.router.datasync.server.codec.CodecUtil;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * SyncLogHandler
 * 其他节点会推送同步数据到当前新增的节点，这里接收数据
 *
 * @author tmy
 * @date 2022-05-08 17:20
 */
public class SyncLogClientHandler extends SimpleChannelInboundHandler<SyncLog> {

    private static final Logger logger = LoggerFactory.getLogger(SyncLogClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncLog syncLog) {
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
        logger.info("storage: {}", DataSyncStorage.getStorages());
    }

}
