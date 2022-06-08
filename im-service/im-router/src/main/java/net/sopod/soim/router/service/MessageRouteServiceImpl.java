package net.sopod.soim.router.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sopod.soim.logic.common.model.message.UserMessage;
import net.sopod.soim.router.api.service.MessageRouteService;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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

    private static final Logger logger = LoggerFactory.getLogger(MessageRouteServiceImpl.class);

    private RouterUserService routerUserService;

    @Override
    public List<Boolean> routeGroupMessage(Long sender, List<Long> uids, String groupMessage) {
        RouterUserStorage storage = RouterUserStorage.getInstance();
        List<Boolean> results = new ArrayList<>(uids.size());
        for (Long uid : uids) {
            RouterUser routerUser = storage.get(uid);
            log.info("route group msg: {}, {}", uid, routerUser);
            if (routerUser == null) {
                results.add(false);
                continue;
            }
            Boolean success = routerUserService.routeGroupMessage(sender, routerUser, groupMessage);
            results.add(Boolean.TRUE.equals(success));
        }
        return results;
    }

    /**
     * 调用该方法时，将到 im-router 服务的路由 uid 设置为消息接受者的 uid
     */
    @Override
    public Boolean routeUserMessage(UserMessage userMessage) {
        RouterUserStorage storage = RouterUserStorage.getInstance();
        RouterUser receiverUser = storage.getRouterUserMap().get(userMessage.getReceiverUid());
        Boolean send = routerUserService.routeChatMessage(userMessage.getSenderUid(), receiverUser, userMessage.getMessage());
        if (!Boolean.TRUE.equals(send)) {
            // 消息未送达
            logger.info("消息未送达: {}: {}", userMessage.getReceiverName(), userMessage.getMessage());
        }
        return Boolean.TRUE.equals(send);
    }

}
