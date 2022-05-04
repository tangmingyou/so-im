package net.sopod.soim.router.api.route;

import net.sopod.soim.common.util.HashAlgorithms;

import java.util.Map;
import java.util.TreeMap;

/**
 * UidConsistentHashSelector
 *
 * @author tmy
 * @date 2022-05-04 10:37
 */
public class UidConsistentHashSelector<V> {

    /**
     * 一致性 hash 每个节点的虚拟节点数量
     */
    private static final int VIRTUAL_NODE_SIZE = 120;

    private final TreeMap<Long, V> virtualNodeMap;

    private final int identityHashCode;

    public UidConsistentHashSelector(Map<String, V> serverAddressMap, int identityHashCode) {
        this.identityHashCode = identityHashCode;
        this.virtualNodeMap = new TreeMap<>();

        // 构建虚拟节点一致性 hash 表
        for (Map.Entry<String, V> entry : serverAddressMap.entrySet()) {
            String serverAddr = entry.getKey();
            V value = entry.getValue();
            for (int i = 0, len = VIRTUAL_NODE_SIZE / 4; i < len; i++) {
                for (int h = 0; h < 4; h++) {
                    long hash = hash(serverAddr + i, h);
                    this.virtualNodeMap.put(hash, value);
                }
            }
        }
    }

    public V select(String uid) {
        if (uid == null) {
            throw new IllegalStateException("im-router consistent hash route, ctx uid can not be null!");
        }
        long hash = hash(uid, 0);
        Map.Entry<Long, V> entry = virtualNodeMap.ceilingEntry(hash);
        if (entry == null) {
            entry = virtualNodeMap.firstEntry();
        }
        return entry.getValue();
    }

    private static long hash(String value, int number) {
        return HashAlgorithms.md5Hash(value, number);
    }

    public int getIdentityHashCode() {
        return identityHashCode;
    }

}
