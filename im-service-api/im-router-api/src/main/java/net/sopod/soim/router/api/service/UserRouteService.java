package net.sopod.soim.router.api.service;

import net.sopod.soim.logic.common.model.TextChat;
import net.sopod.soim.router.api.model.CacheRes;
import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.router.api.model.RegistryRes;

import java.util.List;

/**
 * UserRouteService
 * 缓存登录账号信息，entry 节点信息...
 *
 * @author tmy
 * @date 2022-04-14 15:22
 */
public interface UserRouteService {

    RegistryRes registryUserEntry(Long uid, String imEntryAddr);

    /**
     * 查询在线用户列表
     * @param keyword 查询关键字
     * @return 用户列表
     */
    List<UserInfo> onlineUserList(String keyword);

    /**
     * @param friendId 好友关系id
     * @param textChat 聊天内容
     */
    Boolean routeTextChat(Long friendId, TextChat textChat);

    List<Boolean> isOnlineUsers(List<Long> userIds);

}
