package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SyncCmdServerHandler
 *
 * @author tmy
 * @date 2022-05-08 18:48
 */
public class SyncCmdServerHandler extends SimpleChannelInboundHandler<SyncCmd> {

    private static final Logger logger = LoggerFactory.getLogger(SyncCmdServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncCmd syncCmd) throws Exception {
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
        System.out.println("ping: " + ctx.channel());
        SyncCmd pong = new SyncCmd().setCmdType(SyncCmd.PONG);
        ctx.writeAndFlush(pong);
    }

    private void handlePong(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        logger.info("pong: {}", ctx.channel());
    }

}
