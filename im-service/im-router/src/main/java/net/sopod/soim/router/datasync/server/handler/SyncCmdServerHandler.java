package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.server.SyncLogByHashService;
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
        logger.info("read syncCmd: {}", syncCmd);
        switch (syncCmd.getCmdType()) {
            case SyncCmd.PING:
                this.handlePing(ctx, syncCmd);
                break;
            case SyncCmd.PONG:
                this.handlePong(ctx, syncCmd);
                break;
            case SyncCmd.SYNC_BY_HASH:
                this.handleReqSyncByHash(ctx, syncCmd);
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

    private void handleReqSyncByHash(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        String clientAddr = syncCmd.getParam1();
        // 绑定数据同步服务
        SyncLogByHashService syncLogByHashService = new SyncLogByHashService(clientAddr);
        ctx.channel().attr(SyncLogByHashService.ATTR_KEY).set(syncLogByHashService);
        // 开始数据同步
        syncLogByHashService.startPush();
    }

}
