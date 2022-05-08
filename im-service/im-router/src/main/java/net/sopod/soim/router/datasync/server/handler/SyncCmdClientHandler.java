package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SyncHandler
 *
 * @author tmy
 * @date 2022-05-07 21:43
 */
public class SyncCmdClientHandler extends SimpleChannelInboundHandler<SyncCmd> {

    private static final Logger logger = LoggerFactory.getLogger(SyncCmdClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        switch (syncCmd.getCmdType()) {
            case SyncCmd.PING:
                this.handlePing(ctx, syncCmd);
                break;
            case SyncCmd.PONG:
                this.handlePong(ctx, syncCmd);
                break;
        }
    }

    private void handlePing(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        logger.info("ping: {}", ctx.channel());
        SyncCmd pong = new SyncCmd().setCmdType(SyncCmd.PONG);
        ctx.writeAndFlush(pong);
    }

    private void handlePong(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        logger.info("pong: {}", ctx.channel());
    }

}
