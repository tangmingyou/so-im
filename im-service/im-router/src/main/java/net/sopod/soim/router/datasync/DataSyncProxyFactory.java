package net.sopod.soim.router.datasync;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.datasync.annotation.SyncIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BiSyncProxyManager
 * 设置属性的时候，将设置方法同步处理
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

    @SuppressWarnings("unchecked")
    public static <T extends DataSync> T newProxyInstance(SyncTypes.SyncType<T> syncType) {
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
        return (T) enhancer.create();
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

            // TODO 记录更新操作(方法和参数)，查询数据订阅者，异步同步数据
            DataChangeTrigger.instance().onUpdate(syncType, syncType.getDataKey((T) instance), methodName, args);

            System.out.println("intercept invoke:" + methodName);
            return methodProxy.invokeSuper(instance, args);
        }

    }

    public static interface A {
        default void setName(String name) {
            System.out.println("setName: " + name);
        }

    }

    public static class B implements A {
        private int age;

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public void setName(String name) {
            System.out.println("over setName: " + name);
        }
    }

    public static void main(String[] args) {

        RouterUser routerUser = newProxyInstance(SyncTypes.ROUTER_USER);
        routerUser.setAccount("日月光");
        System.out.println(routerUser.getAccount());

//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(B.class);
//        enhancer.setCallback(new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//                System.out.println("proxy method: " + method.getName());
//                return methodProxy.invokeSuper(o, args);
//            }
//        });

//        B b = (B) enhancer.create();
//        b.setAge(12);
//        b.setName("沧海");
//
//        System.out.println(b.age);
//
//        for (Method method : B.class.getMethods()) {
//            System.out.println(method.getName() + "-" + method.hashCode() + ": " + method.getDeclaringClass());
//        }
//        System.out.println(Serializable.class.isAssignableFrom(String.class));
//        System.out.println(Serializable.class.isAssignableFrom(Integer.class));
//        System.out.println(Integer.class.isAssignableFrom(Serializable.class));
    }

}
