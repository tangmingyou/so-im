package net.sopod.soim.router.service;

import lombok.AllArgsConstructor;
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
public class MessageRouteServiceImpl implements MessageRouteService {

    private RouterUserService routerUserService;

    @Override
    public List<Boolean> routeGroupMessage(List<Long> uids, String message) {
        RouterUserStorage storage = RouterUserStorage.getInstance();
        List<Boolean> results = new ArrayList<>(uids.size());
        Iterator<Long> iterator = uids.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            RouterUser routerUser = storage.get(iterator.next());
            if (routerUser == null) {
                results.set(i, false);
                continue;
            }
            Boolean success = routerUserService.routeGroupMessage(routerUser, message);
            results.set(i, Boolean.TRUE.equals(success));
        }
        return results;
    }

}
