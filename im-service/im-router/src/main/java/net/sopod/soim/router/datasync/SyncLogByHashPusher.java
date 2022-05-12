package net.sopod.soim.router.datasync;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import net.sopod.soim.router.config.AppContextHolder;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.data.SyncLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.*;

/**
 * SyncLogByHashService
 *
 * @author tmy
 * @date 2022-05-10 00:30
 */
public class SyncLogByHashPusher extends DataChangeTrigger.DataKeySyncLogSubscribe {

    private static final Logger logger = LoggerFactory.getLogger(SyncLogByHashPusher.class);

    public static final AttributeKey<SyncLogByHashPusher> ATTR_KEY = AttributeKey
            .valueOf(SyncLogByHashPusher.class, "SYNC_LOG_BY_HASH_PUSHER");

    private final WeakReference<Channel> clientChannel;

    /**
     * 新im-router节点地址
     */
    private final String newNodeAddr;

    private final UidConsistentHashSelector<String> selector;

    public SyncLogByHashPusher(Channel clientChannel, String newNodeAddr) {
        this.clientChannel = new WeakReference<>(clientChannel);
        this.newNodeAddr = newNodeAddr;
        // 构建hash环匹配要迁移的数据
        Map<String, String> twoNodes = new HashMap<>();
        twoNodes.put(newNodeAddr, newNodeAddr);
        twoNodes.put(AppContextHolder.getAppAddr(), AppContextHolder.getAppAddr());
        selector = new UidConsistentHashSelector<>(twoNodes, twoNodes.hashCode());

        // 添加数据变化监听
        DataChangeTrigger.instance().subscribe(this);
    }

    private Map<DataSyncStorage<? extends DataSync>, SyncTypes.SyncType<? extends DataSync>> storages;

    private DataSyncStorage<? extends DataSync> curStorage;

    private Iterator<? extends DataSync> fullDataIterator;

    private int totalCount = 0;

    public void startPush() {
        // 开始数据推送
        this.storages = DataSyncStorage.getStorages();
        if (Collects.isEmpty(this.storages)) {
            this.syncFinishSuccess();
            return;
        }
        this.curStorage = storages.keySet().iterator().next();
        this.fullDataIterator = this.curStorage.getFullDataIterator();

        this.pushNextBatch();
    }

    @SuppressWarnings("unchecked")
    public void pushNextBatch() {
        if (this.fullDataIterator == null) {
            return;
        }
        int count = 0;
        SyncTypes.SyncType<DataSync> syncType = (SyncTypes.SyncType<DataSync>)storages.get(curStorage);
        SyncLog.AddLog<DataSync> addLog = SyncLog.addLog(0, syncType);
        while(fullDataIterator.hasNext()) {
            DataSync data = fullDataIterator.next();
            String dataKey = syncType.getDataKey(data);
            // 保留数据不迁移
            if (!newNodeAddr.equals(selector.select(dataKey))) {
                continue;
            }
            logger.info("sync data: {}", dataKey);
            // 迁移数据
            // TODO 数据加锁
            addLog.addData(data);
            // TODO DataChangeTrigger.instance().subscribe(syncType, dataKey)

            // 注册更改日志
            super.addSubscribeDataKey(syncType, dataKey);

            // TODO 解锁
            count ++; totalCount ++;

            // 批量发送数据
            if (count >= syncType.onceSyncSize()) {
                Channel channel = clientChannel.get();
                if (channel == null || !channel.isActive()) {
                    this.syncFinishFail();
                    return;
                }
                channel.writeAndFlush(addLog);
                // 重新创建对象
                addLog = null;
                break;
            }
        }
        // 最后一点数据
        if (addLog != null) {
            Channel channel = clientChannel.get();
            // 发送数据
            if (channel == null || !channel.isActive()) {
                this.syncFinishFail();
                return;
            }
            channel.writeAndFlush(addLog);
        }
        this.checkNextIterator();
    }

    /**
     * 获取下一个 storage
     */
    private void checkNextIterator() {
        if (this.fullDataIterator != null && this.fullDataIterator.hasNext()) {
            return;
        }
        if (this.curStorage != null) {
            storages.remove(this.curStorage);
        }
        if (Collects.isEmpty(storages)) {
            this.curStorage = null;
            this.fullDataIterator = null;
            this.syncFinishSuccess();
            return;
        }
        this.curStorage = storages.keySet().iterator().next();
        this.fullDataIterator = this.curStorage.getFullDataIterator();
    }

    private void syncFinishFail() {
        logger.error("sync push finish fail!");
    }

    private void syncFinishSuccess() {
        logger.info("sync push finish and success!");
        Channel channel = clientChannel.get();
        if (channel != null && channel.isActive()) {
            SyncCmd syncEndCmd = new SyncCmd();
            syncEndCmd.setCmdType(SyncCmd.SYNC_FULL_END);
            channel.writeAndFlush(syncEndCmd);
        }
    }

    public void releaseResource() {
        // 移除当前环境已同步的数据
        int count = 0;
        for (Map.Entry<SyncTypes.SyncType<?>, Set<String>> entry : syncedTypeDataKyes.entrySet()) {
            SyncTypes.SyncType<?> syncType = entry.getKey();
            for (String dataKey : entry.getValue()) {
                boolean removed = syncType.removeData(dataKey);
                if (removed) {
                    count++;
                }
            }
        }
        logger.info("{}条已同步数据已移除", count);
    }

    /**
     * 修改数据日志
     */
    @Override
    public void onSyncLog(SyncTypes.SyncType<?> syncType, SyncLog syncLog) {
        Channel channel = clientChannel.get();
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(syncLog);
        }
    }

}
