package net.sopod.soim.router.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ImUserCache
 * 在线用户属性缓存
 *
 * @author tmy
 * @date 2022-05-02 14:07
 */
public class SoImUserCache {

    private static final Logger logger = LoggerFactory.getLogger(SoImUserCache.class);

    private static final SoImUserCache INSTANCE = new SoImUserCache();

    private final ConcurrentHashMap<Long, RouterUser> routerUserMap = new ConcurrentHashMap<>(128);

    public static SoImUserCache getInstance() {
        return INSTANCE;
    }

    public RouterUser put(Long uid, RouterUser routerUser) {
        return routerUserMap.put(uid, routerUser);
    }

    public RouterUser get(Long uid) {
        return routerUserMap.get(uid);
    }

    public RouterUser remove(Long uid) {
        return routerUserMap.remove(uid);
    }

    public Map<Long, RouterUser> getRouterUserMap() {
        return routerUserMap;
    }

}
