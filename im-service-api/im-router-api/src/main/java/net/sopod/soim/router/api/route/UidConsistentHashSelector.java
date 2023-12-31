package net.sopod.soim.router.api.route;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

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

    private final TreeMap<Long, Pair<String, V>> virtualNodeMap;

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
                    Pair<String, V> lastNode;
                    if (null == (lastNode = this.virtualNodeMap.get(hash))) {
                        this.virtualNodeMap.put(hash, ImmutablePair.of(serverAddr, value));
                    } else {
                        // hash 冲突取排序小的节点
                        List<String> twoNode = Arrays.asList(lastNode.getLeft(), serverAddr);
                        Collections.sort(twoNode);
                        if (twoNode.get(0).equals(serverAddr)) {
                            this.virtualNodeMap.put(hash, ImmutablePair.of(serverAddr, value));
                        }
                    }
                }
            }
        }
    }

    public V select(String uid) {
        if (uid == null) {
            throw new IllegalStateException("im-router consistent hash route, ctx uid can not be null!");
        }
        long hash = hash(uid, 0);
        Map.Entry<Long, Pair<String, V>> entry = virtualNodeMap.ceilingEntry(hash);
        if (entry == null) {
            entry = virtualNodeMap.firstEntry();
        }
        return entry.getValue().getRight();
    }

    /**
     * 计算加入新节点需要迁移数据的节点
     * @param newNode 新节点地址
     */
    public Set<V> selectMigrateNodes(String newNode) {
        Set<V> nodes = new HashSet<>();
        for (int i = 0, len = VIRTUAL_NODE_SIZE / 4; i < len; i++) {
            for (int h = 0; h < 4; h++) {
                long hash = hash(newNode + i, h);
                Map.Entry<Long, Pair<String, V>> entry = this.virtualNodeMap.ceilingEntry(hash);
                if (entry == null) {
                    entry = this.virtualNodeMap.firstEntry();
                }
                // hash 冲突，取排序最小的一个(老节点)
                if (entry.getKey().equals(hash)) {
                    List<String> twoNode = Arrays.asList(entry.getValue().getLeft(), newNode);
                    Collections.sort(twoNode);
                    // 选中不是该节点跳过
                    if (!twoNode.get(0).equals(newNode)) {
                        continue;
                    }
                }
                nodes.add(entry.getValue().getRight());
            }
        }
        return nodes;
    }

    private static long hash(String value, int number) {
        return md5Hash(value, number);
    }

    private static long md5Hash(String value, int number) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes = value.getBytes(StandardCharsets.UTF_8);
        md5.update(keyBytes);
        byte[] digest = md5.digest();
        // dubbo md5 hash algorithms
        return (((long) (digest[3 + number * 4] & 0xFF) << 24)
                | ((long) (digest[2 + number * 4] & 0xFF) << 16)
                | ((long) (digest[1 + number * 4] & 0xFF) << 8)
                | (digest[number * 4] & 0xFF))
                & 0xFFFFFFFFL;
    }

    public int getIdentityHashCode() {
        return identityHashCode;
    }

    private static volatile UidConsistentHashSelector<?> currentSelector;

    void asCurrent() {
        currentSelector = this;
    }

    public static UidConsistentHashSelector<?> getCurrent() {
//        if (currentSelector == null) {
//            throw new IllegalStateException("当前无运行中的UidConsistentHashSelector");
//        }
        return currentSelector;
    }

}
