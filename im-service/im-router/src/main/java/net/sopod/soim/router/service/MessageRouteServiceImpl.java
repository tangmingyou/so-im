package net.sopod.soim.router.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sopod.soim.router.api.service.MessageRouteService;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * MessageRouterServiceImpl
 * 路由聊天消息
 *
 * @author tmy
 * @date 2022-06-05 11:30
 */
@DubboService
@AllArgsConstructor
@Slf4j
public class MessageRouteServiceImpl implements MessageRouteService {

    private RouterUserService routerUserService;

    @Override
    public List<Boolean> routeGroupMessage(List<Long> uids, String message) {
        RouterUserStorage storage = RouterUserStorage.getInstance();
        List<Boolean> results = new ArrayList<>(uids.size());
        for (Long uid : uids) {
            RouterUser routerUser = storage.get(uid);
            log.info("route group msg: {}, {}", uid, routerUser);
            if (routerUser == null) {
                results.add(false);
                continue;
            }
            Boolean success = routerUserService.routeGroupMessage(routerUser, message);
            results.add(Boolean.TRUE.equals(success));
        }
        return results;
    }

}
