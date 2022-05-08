package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.server.data.SyncLog;

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

    }

}
