package net.sopod.soim.common.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LinkedMapLRUCache
 *
 * @author tmy
 * @date 2022-05-11 17:46
 */
public class LinkedMapLRUCache<K, V> extends LinkedHashMap<K, V> {

    /**
     * 缓存容量
     */
    private final int maxEntries;

    public LinkedMapLRUCache(int maxEntries) {
        this.maxEntries = maxEntries;
    }

    /**
     * 通过重写removeEldestEntry方法，加入一定的条件，满足条件返回true。
     *
     * @param eldest 大链表头节点
     * @return true，表示允许移除头节点；false，表示不允许移除头节点
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        //如果节点数量大于LRU缓存容量，那么返回true
        return size() > maxEntries;
    }

}
