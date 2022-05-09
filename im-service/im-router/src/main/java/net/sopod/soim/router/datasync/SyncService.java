package net.sopod.soim.router.datasync;

import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;

import java.nio.channels.Channel;
import java.util.Map;

/**
 * SyncService
 *
 * @author tmy
 * @date 2022-05-08 09:42
 */
public class SyncService {

    public SyncService(String clientAddr, Channel channel) {

    }

    public void fullSync() {
        Map<Long, RouterUser> routerUserMap = RouterUserStorage.getInstance().getRouterUserMap();
        for (Map.Entry<Long, RouterUser> entry : routerUserMap.entrySet()) {
            Long key = entry.getKey();
            RouterUser value = entry.getValue();
            Jackson.json().serialize(value);

        }
    }

    public static void main(String[] args) {

    }

}
