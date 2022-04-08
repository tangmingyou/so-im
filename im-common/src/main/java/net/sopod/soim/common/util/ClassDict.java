package net.sopod.soim.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com
 * rtbaba
 * msg
 * rpg
 * hehe
 * itbaba
 * msg
 * chat
 */
public class ClassDict {

    /**
     * 顶层叶子节点计数器
     * 26 * 26 二维英文字母表
     * 全类名/URI 128 个字符长，int 消息表
     * a
     *   b
     * a
     *     c
     * int 32bit (a-Z)
     * int * 10
     * aaaQQfff 先把不重复的排一遍，之后再将重复字符依次向后补空位，字符会表示出其他含义，需要冲突补偿
     * 11100011
     * a0 a1 a2 Q0 Q1 f0 f1 f2
     */
    private AtomicInteger counter = new AtomicInteger();

    /**
     * 消息动态字典
     */
    public ConcurrentHashMap<String, Node> dict = new ConcurrentHashMap<>();

    public ClassDict addClass(Class<?> clazz) {
        String fullName = clazz.getName();
        int idx = fullName.indexOf('.'), last = 0;
        Node prev = null;
        for (; idx >= 0; idx = fullName.indexOf('.', last + 1)) {
            String pack = fullName.substring(last, idx);
            last = idx;
            // 第一个节点
            if (prev == null) {
                prev = dict.get(pack);
                if (prev != null) {
                    continue;
                }
                prev = new Node(pack);
                dict.put(pack, prev);
                continue;
            }
            Node node = prev.getChildren().get(pack);
            if (node == null) {
                node = new Node(pack, prev, prev.stage + 1);
                prev.getChildren().put(pack, node);
            }
            prev = node;
        }
        if (last < fullName.length()) {
            String key = fullName.substring(last);
            (prev == null ? dict : prev.getChildren()).put(key, new Node(key, prev, prev == null ? 0 : prev.stage + 1));
        }
        return this;
    }

    public Node get(Class<?> clazz) {
        String fullName = clazz.getName();
        int idx = fullName.indexOf('.'), last = 0;
        Node prev = null;
        for (; idx >= 0; idx = fullName.indexOf('.', last + 1)) {
            String pack = fullName.substring(last, idx);
            last = idx;
            if (prev == null) {
                prev = dict.get(pack);
                if (prev == null)
                    return null;
            }
            prev = prev.getChildren().get(pack);
            if (prev == null)
                return null;
        }
        if (last < fullName.length())
            return prev == null ? null : prev.getChildren().get(fullName.substring(last));
        return prev;
    }

    public static class Node {
        private String key;
        private transient Node prev;
        private int stage;
        private ConcurrentHashMap<String, Node> children = new ConcurrentHashMap<>();

        public Node(String key) {
            this(key, null);
        }

        public Node(String key, Node prev) {
            this(key, prev, 0);
        }

        public Node(String key, Node prev, int stage) {
            this.key = key;
            this.prev = prev;
            this.stage = stage;
        }

        public String getKey() {
            return key;
        }

        public Node getPrev() {
            return prev;
        }

        public Map<String, Node> getChildren() {
            return children;
        }

        public boolean hasChildren() {
            return !children.isEmpty();
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key='" + key + '\'' +
                    ", prev=" + (prev == null ? prev : prev.getKey()) +
                    ", stage=" + stage +
                    ", children=" + children +
                    '}';
        }
    }

    public ConcurrentHashMap<String, Node> getDict() {
        return dict;
    }

    @Override
    public String toString() {
        return "ClassDict{" +
                "dict=" + dict +
                '}';
    }

}
