package net.sopod.soim.router.service;

import net.sopod.soim.entry.api.service.OnlineUserService;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * SoImUserServiceImpl
 *
 * @author tmy
 * @date 2022-05-02 22:45
 */
@DubboService
public class OnlineUserServiceImpl implements OnlineUserService {

    @Override
    public String getImEntryAddrByUid(Long uid) {
        RouterUser routerUser = RouterUserStorage.getInstance().get(uid);
        return routerUser == null ? null : routerUser.getImEntryAddr();
    }

}
