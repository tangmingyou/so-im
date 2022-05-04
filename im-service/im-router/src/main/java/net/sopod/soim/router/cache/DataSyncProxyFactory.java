package net.sopod.soim.router.cache;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sopod.soim.common.util.Collects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * BiSyncProxyManager
 * 设置属性的时候，将设置方法同步处理
 *
 * @author tmy
 * @date 2022-05-04 17:10
 */
public class DataSyncProxyFactory {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncProxyFactory.class);

    @SuppressWarnings("unchecked")
    public static <T extends DataSync> T newProxyInstance(Class<T> biSyncClazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(biSyncClazz);
        enhancer.setCallback(new DataSyncProxyCallback());
        return (T) enhancer.create();
    }

    public static class DataSyncProxyCallback implements MethodInterceptor {

        @Override
        public Object intercept(Object instance, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            String methodName = method.getName();
            // 是判断是否更新的方法跳过
            if ("isUpdateMethod".equals(methodName)) {
                return methodProxy.invokeSuper(instance, args);
            }
            boolean isUpdateMethod = ((DataSync) instance).isUpdateMethod(methodName);
            if (!isUpdateMethod) {
                return methodProxy.invokeSuper(instance, args);
            }
            if (Collects.isNotEmpty(args)) {
                // TODO 参数值可能为 null，检查参数可序列化放在生成代理对象时
                for (int i = 0; i < args.length; i++) {
                    if (!(args[i] instanceof Serializable)) {
                        logger.error("类:{} 更新方法:{} 第{}i个参数不可序列化", instance.getClass(), methodName, i+1);
                    }
                }
            }
            // TODO 记录更新操作(方法和参数)，查询数据订阅者，异步同步数据
            System.out.println("intercept invoke:" + methodName);
            return methodProxy.invokeSuper(instance, args);
        }

    }

    public static void main(String[] args) {
        RouterUser routerUser = newProxyInstance(RouterUser.class);
        routerUser.setAccount("日月光");
        System.out.println(routerUser.getAccount());
    }

}
