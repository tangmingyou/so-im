package net.sopod.soim.router.service;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDasService;
import net.sopod.soim.entry.api.service.TextChatService;
import net.sopod.soim.logic.common.model.TextChat;
import net.sopod.soim.router.api.model.CacheRes;
import net.sopod.soim.router.api.model.RouterUser;
import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.router.api.service.UserEntryRegistryService;
import net.sopod.soim.router.util.ServerContext;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * AccountStoreServiceImpl
 *
 * @author tmy
 * @date 2022-04-28 9:47
 */
@DubboService
public class UserEntryRegistryServiceImpl implements UserEntryRegistryService {

    private static final Logger logger = LoggerFactory.getLogger(UserEntryRegistryServiceImpl.class);

    private final ConcurrentHashMap<Long, RouterUser> uidImEntryStore;

    @DubboReference
    private UserDasService userDasService;

    @DubboReference
    private TextChatService textChatService;

    public UserEntryRegistryServiceImpl() {
        this.uidImEntryStore = new ConcurrentHashMap<>();
    }

    @Override
    public CacheRes registryUserEntry(Long uid, String imEntryAddr) {
        ImUser imUser = userDasService.getUserById(uid);
        RouterUser routerUser = new RouterUser().setUid(uid)
                .setAccount(imUser.getAccount())
                .setIsOnline(Boolean.TRUE)
                .setOnlineTime(ImClock.millis());
        this.uidImEntryStore.put(uid, routerUser);
        return CacheRes.success(0L);
    }


    public List<UserInfo> onlineUserList(String keyword) {
        logger.info("client context uid: {}", ServerContext.getContextUid());

        Stream<RouterUser> stream = uidImEntryStore.values().stream();
        if (!StringUtil.isEmpty(keyword)) {
            // 根据关键词过滤
            stream = stream.filter(user -> user.getAccount().contains(keyword));
        }
        return stream.map(user -> new UserInfo().setUid(user.getUid()).setAccount(user.getAccount()))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean routeTextChat(TextChat textChat) {
        Long receiverUid = textChat.getReceiverUid();
        // TODO 负载均衡路由 receiver 所在 entry
        RouterUser routerUser = uidImEntryStore.get(receiverUid);
        if (routerUser == null) {
            return Boolean.FALSE;
        }
        Boolean send = textChatService.sendTextChat(textChat);
        if (!Boolean.TRUE.equals(send)) {
            // 未送到，消息存储，重发...
            logger.info("消息未送达: {}: {}", textChat.getReceiverName(), textChat.getMessage());
        }
        return Boolean.TRUE;
    }

}
