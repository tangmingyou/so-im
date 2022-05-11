package net.sopod.soim.router.datasync;

import net.sopod.soim.router.config.ImRouterAppOnReady;
import net.sopod.soim.router.datasync.server.data.SyncLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DataUpdateTrigger
 * 序列化后，放入处理异步队列发送给监听者
 *
 * @author tmy
 * @date 2022-05-05 16:14
 */
public class DataChangeTrigger {

    private static final Logger logger = LoggerFactory.getLogger(DataChangeTrigger.class);

    private static DataChangeTrigger INSTANCE;

    // TODO subscribes 订阅 selector.select(dataKey);

    public static DataChangeTrigger instance() {
        if (INSTANCE == null) {
            synchronized (DataChangeTrigger.class) {
                if (INSTANCE == null) {
                     INSTANCE = new DataChangeTrigger();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * <dataKey, 数据日志序列号器>
     * TODO 优化
     *
     */
    private final ConcurrentHashMap<String, AtomicInteger> seqCounterMap = new ConcurrentHashMap<>(1024);

    /**
     * 监听者列表
     */
    private final List<SyncLogSubscribe> subscribes = new ArrayList<>();

    private List<SyncLogSubscribe> getSubscribes(String dataKey) {
        if (subscribes.isEmpty()) {
            return Collections.emptyList();
        }
        List<SyncLogSubscribe> acceptSubs = new ArrayList<>(5);
        for (SyncLogSubscribe subscribe : subscribes) {
            if (subscribe.accept(dataKey)) {
                acceptSubs.add(subscribe);
            }
        }
        return acceptSubs;
    }

    public <T extends DataSync> void onUpdate(SyncTypes.SyncType<T> syncType, String dataKey, String method, Object[] args) {
        List<SyncLogSubscribe> acceptSubs;
        if ((acceptSubs = getSubscribes(dataKey)).isEmpty()) {
            return;
        }
        // 序列化 args，避免后续更改
        SyncLog.UpdateLog<T> updateLog = SyncLog.updateLog(getSeq(dataKey), syncType)
                .setDataKey(dataKey)
                .setMethod(method)
                .setArgs(args);
        publishLog(updateLog, acceptSubs);

    }

    public <T extends DataSync> void onAdd(SyncTypes.SyncType<T> syncType, T data) {
        List<SyncLogSubscribe> acceptSubs;
        String dataKey = syncType.getDataKey(data);
        if ((acceptSubs = getSubscribes(dataKey)).isEmpty()) {
            return;
        }
        AtomicInteger seqCounter = getSeqCounter(dataKey);
        // 序列化 data，避免后续更改
        SyncLog.AddLog<T> addLog = SyncLog.addLog(seqCounter.getAndIncrement(), syncType)
                .addData(data);
        publishLog(addLog, acceptSubs);
    }

    /**
     * 数据删除日志
     */
    public <T extends DataSync> void onRemove(SyncTypes.SyncType<T> syncType, String dataKey) {
        List<SyncLogSubscribe> acceptSubs;
        if ((acceptSubs = getSubscribes(dataKey)).isEmpty()) {
            return;
        }
        SyncLog.RemoveLog<T> removeLog = SyncLog.removeLog(getSeq(dataKey), syncType)
                .setDataKey(dataKey);
        publishLog(removeLog, acceptSubs);
    }

    private void publishLog(SyncLog log, List<SyncLogSubscribe> acceptSubs) {
        for (SyncLogSubscribe acceptSub : acceptSubs) {
            try {
              acceptSub.onSyncLog(log);
            } catch (Exception e) {
                logger.error("监听者 {} 执行错误", acceptSub, e);
            }
        }
    }

    private int getSeq(String dataKey) {
        return getSeqCounter(dataKey).getAndIncrement();
    }

    private AtomicInteger getSeqCounter(String dataKey) {
        return seqCounterMap.computeIfAbsent(dataKey, key -> new AtomicInteger());
    }

    /**
     * 添加更改日志监听者
     */
    public void addSubscribe(SyncLogSubscribe syncLogSubscribe) {
        subscribes.add(syncLogSubscribe);
    }

    /**
     * 删除暂存数据字段等...
     */
    public void clean() {

    }

    public static interface SyncLogSubscribe {

        public abstract boolean accept(String dataKey);

        public abstract void onSyncLog(SyncLog syncLog);

    }

    public static abstract class DataKeySyncLogSubscribe implements SyncLogSubscribe {

        private final Set<String> subscribeDataKeys;

        public DataKeySyncLogSubscribe() {
            this.subscribeDataKeys = new HashSet<>(1024);
        }

        @Override
        public boolean accept(String dataKey) {
            return subscribeDataKeys.contains(dataKey);
        }

        public void addSubscribeDataKey(String dataKey) {
            subscribeDataKeys.add(dataKey);
        }

        @Override
        public abstract void onSyncLog(SyncLog syncLog);

    }

}
