package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.server.data.SyncLog;

/**
 * SyncLogHandler
 * 其他节点会推送同步数据到当前新增的节点，这里接收数据
 *
 * @author tmy
 * @date 2022-05-08 17:20
 */
public class SyncLogClientHandler extends SimpleChannelInboundHandler<SyncLog> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SyncLog syncLog) throws Exception {

    }

}
