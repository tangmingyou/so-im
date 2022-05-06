package net.sopod.soim.router.cache;

import net.sf.cglib.proxy.Enhancer;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.router.datasync.DataChangeTrigger;
import net.sopod.soim.router.datasync.DataSyncProxyFactory;
import net.sopod.soim.router.datasync.SyncTypes;
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
        // TODO 这里克隆一个代理对象
        if (Enhancer.isEnhanced(routerUser.getClass())) {
            routerUserMap.put(uid, routerUser);
            return routerUser;
        }
        RouterUser proxyRouterUser = DataSyncProxyFactory.newProxyInstance(SyncTypes.ROUTER_USER);

        // 新增数据触发
        DataChangeTrigger.instance().onAdd(SyncTypes.ROUTER_USER, routerUser);
        return routerUserMap.put(uid, routerUser);
    }

    public RouterUser get(Long uid) {
        return routerUserMap.get(uid);
    }

    public RouterUser remove(Long uid) {
        if (uid != null) {
            DataChangeTrigger.instance().onRemove(SyncTypes.ROUTER_USER, StringUtil.toString(uid));
            return routerUserMap.remove(uid);
        }
        return null;
    }

    public Map<Long, RouterUser> getRouterUserMap() {
        return routerUserMap;
    }

}
