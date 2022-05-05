package net.sopod.soim.router.datasync.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SyncLogInboundHandler
 *
 * @author tmy
 * @date 2022-05-05 14:57
 */
public class SyncLogInboundHandler extends SimpleChannelInboundHandler<SyncLog> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SyncLog syncLog) throws Exception {
        
    }

}
