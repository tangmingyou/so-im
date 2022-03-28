package net.sopod.soim.core.registry;

import net.sopod.soim.core.service.ReqHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ServiceRegistry
 *
 * @author tmy
 * @date 2022-03-28 14:30
 */
public class ServiceRegistry {

    private static final ConcurrentHashMap<Integer, Class<?>>  serviceIdParamTypeMap;

    private static final ConcurrentHashMap<Class<?>, Integer>  paramTypeServiceIdMap;

    private static final ConcurrentHashMap<Integer, ReqHandler<?>>  serviceIdHandlers;

    static {
        serviceIdParamTypeMap = new ConcurrentHashMap<>();
        paramTypeServiceIdMap = new ConcurrentHashMap<>();
        serviceIdHandlers = new ConcurrentHashMap<>();
    }

    private static final AtomicInteger serviceIdGen = new AtomicInteger(10000);

    private static <T> void registry(Class<T> paramType, ReqHandler<T> handler) {
        int serviceId = serviceIdGen.getAndIncrement();
        serviceIdParamTypeMap.put(serviceId, paramType);
        paramTypeServiceIdMap.put(paramType, serviceId);
        serviceIdHandlers.put(serviceId, handler);
    }

    public void aaa() {

    }

}
