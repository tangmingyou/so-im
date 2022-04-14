package net.sopod.soim.router.service;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.router.api.model.CacheRes;
import net.sopod.soim.router.api.service.AuthStoreService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * AuthStoreServiceImpl
 *
 * @author tmy
 * @date 2022-04-14 11:23
 */
@DubboService
public class AuthStoreServiceImpl implements AuthStoreService {

    private final ConcurrentHashMap<Long, String> tokenStore;

    public AuthStoreServiceImpl() {
        tokenStore = new ConcurrentHashMap<>();
    }

    @Override
    public CacheRes cacheToken(Long userId, String token) {
        // 从内存中获取
        tokenStore.put(userId, token);

        // 从redis中获取
        return CacheRes.success(ImClock.millis() + 120_1000);
    }

}
