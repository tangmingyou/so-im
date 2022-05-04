package net.sopod.soim.router.cache;

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

    private static final SoImUserCache INSTANCE = new SoImUserCache();

    private final ConcurrentHashMap<Long, RouterUser> routerUserMap = new ConcurrentHashMap<>(128);

    public static SoImUserCache getInstance() {
        return INSTANCE;
    }

    public void put(Long uid, RouterUser routerUser) {
        routerUserMap.put(uid, routerUser);
    }

    public RouterUser get(Long uid) {
        return routerUserMap.get(uid);
    }

    public Map<Long, RouterUser> getRouterUserMap() {
        return routerUserMap;
    }

}
