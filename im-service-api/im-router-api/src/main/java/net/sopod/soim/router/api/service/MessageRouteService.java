package net.sopod.soim.router.api.service;

import java.util.List;

/**
 * MessageRouteService
 *
 * @author tmy
 * @date 2022-06-05 11:28
 */
public interface MessageRouteService {

    List<Boolean> routeGroupMessage(List<Long> uids, String message);

}
