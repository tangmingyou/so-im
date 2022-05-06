package net.sopod.soim.router.datasync;

import net.sopod.soim.router.datasync.server.SyncLog;

import java.util.Queue;
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

    private static DataChangeTrigger INSTANCE;

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

    private final ConcurrentHashMap<String, AtomicInteger> seqCounterMap = new ConcurrentHashMap<>(128);

    private final Queue<SyncLog> logQueue = new ConcurrentLinkedQueue<>();

    public <T extends DataSync> void onUpdate(SyncTypes.SyncType<T> syncType, String dataKey, String method, Object[] args) {
        // 序列化 args，避免后续更改
        SyncLog.UpdateLog<T> updateLog = SyncLog.updateLog(getSeq(dataKey), syncType)
                .setDataKey(dataKey)
                .setMethod(method)
                .setArgs(args);
        publishLog(updateLog);
    }

    public <T extends DataSync> void onAdd(SyncTypes.SyncType<T> syncType, T data) {
        String dataKey = syncType.getDataKey(data);
        AtomicInteger seqCounter = getSeqCounter(dataKey);
        // 序列化 data，避免后续更改
        SyncLog.AddLog<T> addLog = SyncLog.addLog(seqCounter.getAndIncrement(), syncType)
                .addData(data);
        publishLog(addLog);
    }

    /**
     * 数据删除日志
     */
    public <T extends DataSync> void onRemove(SyncTypes.SyncType<T> syncType, String dataKey) {
        SyncLog.RemoveLog<T> removeLog = SyncLog.removeLog(getSeq(dataKey), syncType)
                .setDataKey(dataKey);
        publishLog(removeLog);
    }

    private void publishLog(SyncLog log) {
        // TODO 判断无订阅者跳过，新增节点同步按 dataKey 单独订阅每一个数据更新
        logQueue.add(log);
        System.out.println("publish log: "+log);
    }

    private int getSeq(String dataKey) {
        return getSeqCounter(dataKey).getAndIncrement();
    }

    private AtomicInteger getSeqCounter(String dataKey) {
        return seqCounterMap.computeIfAbsent(dataKey, key -> new AtomicInteger());
    }

}
