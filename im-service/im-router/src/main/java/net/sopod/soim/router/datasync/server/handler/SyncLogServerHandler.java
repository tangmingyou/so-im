package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.SyncTypes;
import net.sopod.soim.router.datasync.server.codec.CodecUtil;
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SyncLog syncLog) throws Exception {
        List<byte[]> bytesList = syncLog.getSerializeDataCollect();
        for (byte[] bytes : bytesList) {
            SyncTypes.SyncType<DataSync> syncType = SyncTypes.getSyncType(syncLog.getSyncType());
            DataSync instance = CodecUtil.decode(bytes, syncType.dataType());
            System.out.println(instance);
        }
        System.out.println("read: " + syncLog);
    }

}
