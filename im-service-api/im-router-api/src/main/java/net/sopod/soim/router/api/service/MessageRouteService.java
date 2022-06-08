package net.sopod.soim.router.api.service;

import net.sopod.soim.logic.common.model.message.UserMessage;

import java.util.List;

/**
 * MessageRouteService
 *
 * @author tmy
 * @date 2022-06-05 11:28
 */
public interface MessageRouteService {

    List<Boolean> routeGroupMessage(Long sender, List<Long> uids, String message);

    /**
     * @param textChat 聊天内容
     */
    Boolean routeUserMessage(UserMessage textChat);

}
