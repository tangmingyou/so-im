package net.sopod.soim.router.datasync.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * SyncLogInboundHandler
 *
 * @author tmy
 * @date 2022-05-05 14:57
 */
public class SyncServerInboundHandler extends ChannelInboundHandlerAdapter {

    public SyncServerInboundHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
