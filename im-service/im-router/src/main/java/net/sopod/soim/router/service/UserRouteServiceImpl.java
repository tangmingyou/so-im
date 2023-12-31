package net.sopod.soim.router.service;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.entry.api.service.OnlineUserService;
import net.sopod.soim.entry.api.service.TextChatService;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.router.api.model.RegistryRes;
import net.sopod.soim.router.api.service.UserRouteService;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import net.sopod.soim.router.config.ImRouterAppContext;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * UserRouteServiceImpl
 *
 * @author tmy
 * @date 2022-04-28 9:47
 */
@DubboService
public class UserRouteServiceImpl implements UserRouteService {

    private static final Logger logger = LoggerFactory.getLogger(UserRouteServiceImpl.class);

    @DubboReference
    private UserDas userDas;

    @DubboReference
    private FriendDas friendDas;

    @DubboReference
    private TextChatService textChatService;

    @DubboReference
    private OnlineUserService onlineUserService;

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @Override
    public RegistryRes registryUserEntry(Long uid, String imEntryAddr) {
        ImUser imUser = userDas.getUserById(uid);
        logger.info("registry user:{}, {}", uid, imUser.getAccount());
        RouterUser routerUser = new RouterUser().setUid(uid)
                .setAccount(imUser.getAccount())
                .setIsOnline(Boolean.TRUE)
                .setOnlineTime(ImClock.millis())
                .setImEntryAddr(imEntryAddr);
        RouterUserStorage.getInstance().put(uid, routerUser);
        // 接口返回 im_router_id，后续调用 im-router 负载均衡指向当前router服务
        return new RegistryRes()
                .setSuccess(true)
                .setImRouterId(ImRouterAppContext.IM_ROUTER_ID);
    }

    @Override
    public List<UserInfo> onlineUserList(String keyword) {
        //logger.info("client context uid: {}", ServerContext.getContextUid());
        logger.info("client context uid: {}", RpcContext.getServiceContext().getAttachment(DubboConstant.CTX_UID));

        Stream<RouterUser> stream = RouterUserStorage.getInstance().getRouterUserMap().values().stream();
        if (!StringUtil.isEmpty(keyword)) {
            // 根据关键词过滤
            stream = stream.filter(user -> user.getAccount().contains(keyword));
        }
        return stream.map(user -> new UserInfo().setUid(user.getUid()).setAccount(user.getAccount()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Boolean> isOnlineUsers(List<Long> userIds) {
        int len = userIds.size();
        List<Boolean> isOnlines = new ArrayList<>(len);
        RouterUserStorage userStorage = RouterUserStorage.getInstance();
        for (int i = 0; i < len; i++) {
            RouterUser routerUser = userStorage.get(userIds.get(i));
            isOnlines.add(routerUser != null && Boolean.TRUE.equals(routerUser.getIsOnline()));
        }
        return isOnlines;
    }

}
