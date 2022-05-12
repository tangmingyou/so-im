package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.router.datasync.DataChangeTrigger;
import net.sopod.soim.router.datasync.SyncLogByHashPusher;
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
            case SyncCmd.SYNC_BY_HASH_ACK:
                this.handleReqSyncByHashAck(ctx, syncCmd);
                break;
            case SyncCmd.SYNC_FINISH_CLOSE:
                this.handleSyncFinishClose(ctx, syncCmd);
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
        SyncLogByHashPusher syncLogByHashPusher = new SyncLogByHashPusher(ctx.channel(), clientAddr);
        ctx.channel().attr(SyncLogByHashPusher.ATTR_KEY).set(syncLogByHashPusher);
        // 开始数据同步
        syncLogByHashPusher.startPush();
    }

    /**
     * 推送数据响应，推送下一批数据
     */
    private void handleReqSyncByHashAck(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        SyncLogByHashPusher syncLogByHashPusher = ctx.channel().attr(SyncLogByHashPusher.ATTR_KEY).get();
        syncLogByHashPusher.pushNextBatch();
    }

    private void handleSyncFinishClose(ChannelHandlerContext ctx, SyncCmd syncCmd) {
        SyncLogByHashPusher syncLogByHashPusher = ctx.channel().attr(SyncLogByHashPusher.ATTR_KEY).get();
        // 取消修改监听
        DataChangeTrigger.instance().unsubscribe(syncLogByHashPusher);
        // 移除已迁移的数据
        syncLogByHashPusher.releaseResource();
        // 断开连接
        ctx.channel().close();
        logger.info("channel@{} 同步结束,资源已释放", ctx.channel().id());
    }

}
