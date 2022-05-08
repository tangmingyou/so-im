package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.server.data.SyncCmd;

/**
 * SyncCmdServerHandler
 *
 * @author tmy
 * @date 2022-05-08 18:48
 */
public class SyncCmdServerHandler extends SimpleChannelInboundHandler<SyncCmd> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SyncCmd syncCmd) throws Exception {

    }

}
