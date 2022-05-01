package net.sopod.soim.entry.config;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MsgHandlerContext
 *
 * 线程上下文属性设置工具
 * 这里主要用于 {@link net.sopod.soim.core.handler.MessageHandler} 与 dubbo RpcContext 上下文参数传递
 *
 * @author tmy
 * @date 2022-05-01 22:42
 */
public class MessageHandlerContext {

    private static final FastThreadLocal<Map<String, String>> HOLDER = new FastThreadLocal<>();

    public static void setAttribute(String key, String value) {
        Map<String, String> attrMap;
        if ((attrMap = HOLDER.get()) == null) {
            synchronized (Thread.currentThread()) {
                if ((attrMap = HOLDER.get()) == null) {
                    attrMap = new ConcurrentHashMap<>(3);
                    HOLDER.set(attrMap);
                }
            }
        }
        attrMap.put(key, value);
    }

    public static String getAttribute(String key) {
        Map<String, String> attrMap;
        if ((attrMap = HOLDER.get()) != null) {
            return attrMap.get(key);
        }
        return null;
    }

    public static void remove() {
        HOLDER.remove();
    }

    public static void removeAttribute(String key) {
        Map<String, String> attrMap;
        if ((attrMap = HOLDER.get()) != null) {
            attrMap.remove(key);
        }
    }

}
