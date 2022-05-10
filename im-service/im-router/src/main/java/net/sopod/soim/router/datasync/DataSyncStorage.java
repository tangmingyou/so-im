package net.sopod.soim.router.datasync;

import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.router.cache.RouterUser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DataSyncStorage
 *
 * @author tmy
 * @date 2022-05-10 10:23
 */
public abstract class DataSyncStorage<T extends DataSync> {

    private static ConcurrentHashMap<DataSyncStorage<? extends DataSync>, SyncTypes.SyncType<? extends DataSync>> STORAGES = new ConcurrentHashMap<>();

    public static Map<DataSyncStorage<? extends DataSync>, SyncTypes.SyncType<? extends DataSync>> getStorages() {
        return new HashMap<>(STORAGES);
    }

    protected SyncTypes.SyncType<T> syncType;

    /**
     * 注册可数据数据容器
     */
    protected void registry(SyncTypes.SyncType<T> syncType, DataSyncStorage<T> storage) {
        STORAGES.put(storage, syncType);
        this.syncType = syncType;
    }

    public abstract Iterator<T> getFullDataIterator();

    public void onDataAdd(T data) {
        // TODO...
        DataChangeTrigger.instance().onAdd(syncType, data);
    }

    public void onDataRemove(String dataKey) {
        // TODO...
         DataChangeTrigger.instance().onRemove(SyncTypes.ROUTER_USER, dataKey);
    }

}
