package net.sopod.soim.router.api.service;

import net.sopod.soim.router.api.model.CacheRes;

/**
 * RouterService
 *
 * @author tmy
 * @date 2022-04-14 11:22
 */
public interface AuthStoreService {

    CacheRes cacheToken(Long userId, String token);

}
