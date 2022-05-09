package net.sopod.soim.router.api.route;

import net.sopod.soim.common.util.HashAlgorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ConsistentHashTest
 *
 * @author tmy
 * @date 2022-05-01 15:33
 */
public class ConsistentHashTest {

    private static final int VIRTUAL_NODE_SIZE = 120;

    private TreeMap<Long, String> virtualInvokers = new TreeMap<>();

    public void add(String key, String value) {
        // 增加虚拟节点n个
        for (int i = 0; i < VIRTUAL_NODE_SIZE / 4; i++) {
            for (int h = 0; h < 4; h++) {
                long virHash = hash(key + i);
                virtualInvokers.put(virHash, value);
            }
//            long virHash = hash("vir-" + key + i);
//            virtualInvokers.put(virHash, value);
        }
        // virtualInvokers.put(hash(key), value);
    }

    public String getFirstNode(String value) {
        long hash = hash(value);
        Map.Entry<Long, String> entry = virtualInvokers.ceilingEntry(hash);
        if (entry != null) {
            return entry.getValue();
        }
        if (virtualInvokers.size() == 0) {
            throw new IllegalStateException("server is not available");
        }
        return virtualInvokers.firstEntry().getValue();
    }

    /**
     * hash 运算
     */
    public static long hash(String value) {
        return HashAlgorithms.md5Hash(value);
    }

    public static long hash(String value, int number) {
        return HashAlgorithms.md5Hash(value, number);
    }

    private static void testConsistentHash() {
        String[] nodes = {
                "192.168.31.156:3032",
                "192.168.31.156:3031",
//                "192.168.1.101",
//                "192.168.1.102",
//                "192.168.1.103",
//                "192.168.1.104",
//                "192.168.1.105",
        };
        ConsistentHashTest route = new ConsistentHashTest();
        for (String node : nodes) {
            route.add(node, node);
        }
        Map<String, AtomicInteger> counter = new HashMap<>();
        for (long i = 71000L; i < 72000L; i++) {
            String node = route.getFirstNode(String.valueOf(i));
            AtomicInteger count = counter.computeIfAbsent(node, k -> new AtomicInteger());
            count.incrementAndGet();
        }
        System.out.println(route.virtualInvokers);
        System.out.println(counter);
    }

    private static void testTreeMap() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(10, "沧海1");
        map.put(20, "沧海2");
        map.put(30, "沧海3");
        map.put(40, "沧海4");
        map.put(50, "沧海5");
        System.out.println(map);
        System.out.println(map.ceilingKey(30));
        System.out.println(map.higherEntry(30));
    }

    public static void main(String[] args) {
        // {192.168.1.103=32, 192.168.1.100=26, 192.168.1.101=19, 192.168.1.102=23}
        // testConsistentHash();
        testTreeMap();
    }


}
