package net.sopod.soim.router.service;

import com.github.benmanes.caffeine.cache.*;
import com.google.common.collect.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * CaffeineTest
 *
 * @author tmy
 * @date 2022-04-21 12:35
 */
public class CaffeineTest {

    public static void test1() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .initialCapacity(128)
                .maximumSize(1024)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(@Nullable String key, @Nullable String value, RemovalCause cause) {
                        System.out.println("remove: " + key + "," + value + ";" + cause.name());
                    }
                })
                .build();
        cache.put("name", "二狗子");
        System.out.println(cache.getIfPresent("name"));
        System.out.println(cache.getIfPresent("天道"));
        TimeUnit.SECONDS.sleep(11);
        System.out.println(cache.getIfPresent("name"));
        System.out.println(cache.stats());
    }

    public static void test2() {
        AsyncCache<String, String> asyncCache = Caffeine.newBuilder()
                .initialCapacity(128)
                .maximumSize(1024)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .buildAsync();

    }

    public static void main(String[] args) {
        HashBasedTable<String, String, Integer> table = HashBasedTable.create();
        table.put("二年级", "狗蛋", 89);
        table.put("一年级", "阿花", 76);
        table.put("二年级", "阿花", 91);
        System.out.println(table.get("二年级", "狗蛋"));
        System.out.println(table.get("一年级", "阿花"));
        System.out.println(table.rowKeySet());
        System.out.println(table.columnKeySet());
        System.out.println(table.values());

        HashBiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("name", 12);
        System.out.println(biMap.get("name"));

        BiMap<Integer, String> inverseMap = biMap.inverse();
        System.out.println(inverseMap.get(12));

        inverseMap.forcePut(60, "name"); // put替换原有的值
        // inverseMap.put(120, "name"); // 已存在会报错
        System.out.println(biMap);

        Multimap<String, Integer> multiMap = ArrayListMultimap.create();
        multiMap.put("day", 1);
        multiMap.put("day", 2);
        multiMap.put("day", 3);
        multiMap.put("day", 2);
        multiMap.put("month", 3);
        System.out.println(multiMap);
    }

}
