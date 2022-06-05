package net.sopod.soim.router.service;

import net.sopod.soim.router.api.service.MessageRouteService;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * MessageRouterServiceImpl
 *
 * @author tmy
 * @date 2022-06-05 11:30
 */
@DubboService
public class MessageRouteServiceImpl implements MessageRouteService {

    @Override
    public List<Boolean> routeGroupMessage(List<Long> uids, String message) {
        // TODO
        RouterUserStorage storage = RouterUserStorage.getInstance();
        for (Long uid : uids) {
            RouterUser routerUser = storage.get(uid);

        }
        return null;
    }

}
