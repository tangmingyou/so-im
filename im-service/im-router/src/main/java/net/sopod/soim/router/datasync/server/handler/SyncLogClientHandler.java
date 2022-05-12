package net.sopod.soim.router.datasync.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.common.util.Reflects;
import net.sopod.soim.common.util.cache.LinkedMapLRUCache;
import net.sopod.soim.router.cache.RouterUserStorage;
import net.sopod.soim.router.datasync.DataSync;
import net.sopod.soim.router.datasync.SyncTypes;
import net.sopod.soim.router.datasync.server.codec.CodecUtil;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * SyncLogHandler
 * 其他节点会推送同步数据到当前新增的节点，这里接收数据
 *
 * @author tmy
 * @date 2022-05-08 17:20
 */
public class SyncLogClientHandler extends SimpleChannelInboundHandler<SyncLog> {

    private static final Logger logger = LoggerFactory.getLogger(SyncLogClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncLog syncLog) {
        switch (syncLog.getOperateType()) {
            case SyncLog.OPT_ADD:
                this.handleAddSyncLog(ctx, syncLog);
                break;
            case SyncLog.OPT_REMOVE:
                this.handleRemoveSyncLog(ctx, syncLog);
                break;
            case SyncLog.OPT_UPDATE:
                this.handleUpdateSyncLog(ctx, syncLog);
                break;
        }
    }

    private void handleAddSyncLog(ChannelHandlerContext ctx, SyncLog syncLog) {
        SyncTypes.SyncType<DataSync> syncType = SyncTypes.getSyncType(syncLog.getSyncType());
        List<byte[]> bytesList = syncLog.getSerializeDataCollect();
        if (Collects.isNotEmpty(bytesList)) {
            for (byte[] bytes : bytesList) {
                DataSync instance = CodecUtil.decode(bytes, syncType.dataType());
                syncType.addData(instance);
                logger.info("addData: {}", instance);
            }
        }
        // 同步完成响应
        SyncCmd syncCmd = new SyncCmd().setCmdType(SyncCmd.SYNC_BY_HASH_ACK);
        ctx.writeAndFlush(syncCmd);
        logger.info("users size: {}", RouterUserStorage.getInstance().getRouterUserMap().size());
    }

    private void handleRemoveSyncLog(ChannelHandlerContext ctx, SyncLog syncLog) {
        SyncTypes.SyncType<DataSync> syncType = SyncTypes.getSyncType(syncLog.getSyncType());
        syncType.removeData(syncLog.getDataKey());
    }

    private static final LinkedMapLRUCache<String, Method> syncTypeMethodCache = new LinkedMapLRUCache<>(16);

    private void handleUpdateSyncLog(ChannelHandlerContext ctx, SyncLog syncLog) {
        SyncTypes.SyncType<DataSync> syncType = SyncTypes.getSyncType(syncLog.getSyncType());
        String methodName = syncLog.getMethod();
        String[] args = syncLog.getArgs();
        Class<DataSync> dataType = syncType.dataType();
        String cacheKey = dataType.getName() + "." + methodName + "(" + args.length + ")";
        Method method = syncTypeMethodCache.get(cacheKey);
        if (method == null) {
            synchronized(syncTypeMethodCache) {
                if (null == (method = syncTypeMethodCache.get(cacheKey))) {
                    List<Method> methods = Reflects.getNonstaticMethods(dataType, methodName, args.length);
                    if (Collects.isEmpty(methods)) {
                        logger.warn("{} method {} not found!", dataType, methodName);
                        return;
                    }
                    method = methods.get(0);
                    syncTypeMethodCache.put(cacheKey, method);
                }
            }
        }
        Type[] types = method.getGenericParameterTypes();
        Object[] params = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            params[i] = Jackson.json().readWithType(args[i], types[i]);
        }
        DataSync data = syncType.getData(syncLog.getDataKey());
        try {
            method.setAccessible(true);
            method.invoke(data, params);
            logger.info("invoke:{},  args:{}", method.getName(), params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("failed invoke {}.{} args:{} ", dataType, method.getName(), params, e);
        }
    }



}
