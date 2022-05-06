package net.sopod.soim.router.datasync;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.datasync.annotation.SyncIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BiSyncProxyManager
 * 设置属性的时候，将设置方法同步处理
 *
 * 分段[cache0,cache2,...cache31] id取模定位, lockMap0[okId1, okId2] 加读写锁，避免序列化时加锁影响全部数据
 * 数据序列化时，get加锁，删除加锁，新增加锁，代理类更新方法加锁
 *
 * @author tmy
 * @date 2022-05-04 17:10
 */
public class DataSyncProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncProxyFactory.class);

    /**
     * 缓存数据类型更新方法列表
     */
    private static final Map<Class<? extends DataSync>, Set<String>> typeUpdaterMethodsCache = new ConcurrentHashMap<>();

    public static <T extends DataSync> T newProxyInstance(SyncTypes.SyncType<T> syncType) {
        return newProxyInstance(syncType, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends DataSync> T newProxyInstance(SyncTypes.SyncType<T> syncType, T source) {
        Class<T> type = syncType.dataType();
        T instance;
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException(type + "实例创建失败,没有无参构造函数", e);
        }

        // 获取查询更新方法列表
        Set<String> updaterMethods = typeUpdaterMethodsCache.computeIfAbsent(type, dataType -> {
            Method[] methods = dataType.getMethods();
            Set<String> updaterMethodNames = new HashSet<>();
            for (Method m : methods) {
                SyncIgnore syncIgnore = m.getDeclaredAnnotation(SyncIgnore.class);
                String methodName = m.getName();
                if (syncIgnore != null) {
                    continue;
                }
                boolean isUpdater = m.getDeclaringClass() != Object.class
                        && instance.isUpdateMethod(methodName);
                if (isUpdater) {
                    if (updaterMethodNames.contains(methodName)) {
                        throw new IllegalStateException(dataType + "重复的数据更新方法名" + methodName);
                    }
                    // 检查参数可序列化
                    Class<?>[] paramTypes = m.getParameterTypes();
                    for (int i = 0; i < paramTypes.length; i++) {
                        if (!Jackson.json().canSerialize(paramTypes[i])) {
                            throw new IllegalStateException(String.format("类:%s 更新方法:%s 第%di个参数不可序列化",
                                    instance.getClass().getName(), methodName, i + 1));
                        }
                    }
                    updaterMethodNames.add(methodName);
                }
            }
            logger.info("{} 更新方法列表: {}", dataType, updaterMethodNames);
            return updaterMethodNames;
        });

        // 创建代理对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallback(new DataSyncProxyCallback<>(syncType, updaterMethods));
        T proxyObj = (T) enhancer.create();
        if (source != null) {
            try {
                cloneFields(source, proxyObj);
            } catch (Exception e) {
                // logger.error("克隆对象失败:", e);
                throw new IllegalStateException("克隆对象失败", e);
            }
        }
        return proxyObj;
    }

    private static <T> void cloneFields(T source, T proxyObj) throws Exception {
        cloneFields0(source.getClass(), source, proxyObj);
    }

    /**
     * 复制对象属性值
     * TODO 缓存 <class,fields> 避免每次clone从方法区查找
     */
    private static <T> void cloneFields0(Class<?> clazz, T source, T target) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        if (Collects.isNotEmpty(fields)) {
            for (Field field : fields) {
                if (!Modifier.isFinal(field.getModifiers())) {
                    field.setAccessible(true);
                    field.set(target, field.get(source));
                }
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != Object.class) {
            cloneFields0(superClazz, source, target);
        }
    }

    public static class DataSyncProxyCallback<T extends DataSync> implements MethodInterceptor {

        private final SyncTypes.SyncType<T> syncType;

        private final Set<String> updaterMethods;

        public DataSyncProxyCallback(SyncTypes.SyncType<T> syncType, Set<String> updaterMethods) {
            this.syncType = syncType;
            this.updaterMethods = updaterMethods;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Object intercept(Object instance, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            String methodName = method.getName();
            // 是判断不是更新的方法跳过
            if (null != method.getDeclaredAnnotation(SyncIgnore.class)
                    || !updaterMethods.contains(methodName)
                    || "isUpdateMethod".equals(methodName)) {
                return methodProxy.invokeSuper(instance, args);
            }

            // 记录数据更新操作(方法和参数)
            DataChangeTrigger.instance().onUpdate(syncType, syncType.getDataKey((T) instance), methodName, args);

            System.out.println("intercept invoke:" + methodName);
            return methodProxy.invokeSuper(instance, args);
        }

    }

    public static void main(String[] args) {
//        RouterUser user1 = new RouterUser();
//        user1.setUid(10086L);
//        user1.setAccount("日月光");
//
//        RouterUser routerUser = newProxyInstance(SyncTypes.ROUTER_USER, user1);
//        System.out.println(routerUser);
//        routerUser.setOnlineTime(ImClock.millis());
//        System.out.println(routerUser);

        ConcurrentHashMap<String, RouterUser> map = new ConcurrentHashMap<>();
        map.put("10081", new RouterUser().setAccount("阿基过天玺"));
        map.put("10082", new RouterUser().setAccount("家国"));
        map.put("10083", new RouterUser().setAccount("天下"));
        Collection<RouterUser> values = map.values();
        map.put("10084", new RouterUser().setAccount("晚风"));
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                map.put("100" + i, new RouterUser().setAccount("灯" + i));
                if (i % 10 == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        int i = 0;

        for (RouterUser value : values) {
            System.out.println(value);
            i++;
            if (i % 3 == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(values.getClass());
        System.out.println(values);

    }

}
