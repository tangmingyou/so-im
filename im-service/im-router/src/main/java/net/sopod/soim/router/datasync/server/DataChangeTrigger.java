package net.sopod.soim.router.datasync.server;

import net.sopod.soim.router.cache.DataSync;

import java.util.Queue;
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

    private static final DataChangeTrigger INSTANCE = new DataChangeTrigger();

    public static DataChangeTrigger instance() {
        return INSTANCE;
    }

    private AtomicInteger seqCounter = new AtomicInteger();

    private Queue<SyncLog> logQueue = new ConcurrentLinkedQueue<>();

    public <T extends DataSync> void onUpdate(SyncTypes.SyncType<T> syncType, String dataKey, String method, Object[] args) {
        // 序列化 args，避免后续更改
        // logQueue.add()
    }

    public <T extends DataSync> void onAdd(SyncTypes.SyncType<T> syncType, T data) {
        // 序列化 data，避免后续更改

    }

    public <T extends DataSync> void onRemove(SyncTypes.SyncType<T> syncType, String dataKey) {

    }

}
