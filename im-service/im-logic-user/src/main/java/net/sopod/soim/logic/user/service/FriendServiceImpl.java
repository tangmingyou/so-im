package net.sopod.soim.logic.user.service;

import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.user.service.FriendService;
import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import net.sopod.soim.router.api.service.UserRouteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * FriendServiceImpl
 *
 * @author tmy
 * @date 2022-05-23 15:44
 */
@DubboService
public class FriendServiceImpl implements FriendService {

    @DubboReference
    private FriendDas friendDas;

    @DubboReference
    private UserDas userDas;

    @DubboReference
    private UserRouteService userRouteService;

    /**
     * TODO 幂等性处理
     */
    @Override
    public CompletableFuture<String> addFriend(Long uid, Long fid) {
        ImUser friend = userDas.getUserById(fid);
        CompletableFuture<String> future = new CompletableFuture<>();
        if (friend == null) {
            return CompletableFuture.completedFuture("不存在的账号");
        }
        if (Boolean.TRUE.equals(friendDas.isExists(uid, fid))) {
            return CompletableFuture.completedFuture("该用户已是你的好友");
        }
        // 添加好友数据
        friendDas.insert(uid, fid);
        // 相互添加为好友
        if (Boolean.FALSE.equals(friendDas.isExists(fid, uid))) {
            friendDas.insert(fid, uid);
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<List<UserInfo>> listFriend(Long uid) {
        List<ImUser> imUsers = friendDas.queryAllFriend(uid);
        // TODO 分组到 im-router 查询在线状态
        UidConsistentHashSelector<?> selector = UidConsistentHashSelector.getCurrent();
        List<UserInfo> userInfos = new ArrayList<>(imUsers.size());
        if (selector == null) {
            // TODO 单实例 im-router, 直接调用
            // TODO 多个实例变成单个后，selector 不为 null
            List<Long> userIds = imUsers.stream().map(ImUser::getId).collect(Collectors.toList());
            List<Boolean> onlineUsers = userRouteService.isOnlineUsers(userIds);
            mappingUserInfos(imUsers, onlineUsers, userInfos);
        } else {
            Map<?, List<ImUser>> userRouteGroup =
                    Collects.group(imUsers, imUser -> selector.select(StringUtil.toString(imUser.getId())), imUser -> imUser);
            for (List<ImUser> users : userRouteGroup.values()) {
                List<Long> userIds = users.stream().map(ImUser::getId).collect(Collectors.toList());
                RpcContextUtil.setContextUid(userIds.get(0));
                // TODO 调用在线状态查询接口，按返回list索引更新在线状态
                List<Boolean> onlineUsers = userRouteService.isOnlineUsers(userIds);
                // TODO 排序
                mappingUserInfos(users, onlineUsers, userInfos);
            }
        }
        return CompletableFuture.completedFuture(userInfos);
    }

    private void mappingUserInfos(List<ImUser> imUsers, List<Boolean> onlineUsers, List<UserInfo> container) {
        for (int i = 0; i < imUsers.size(); i++) {
            ImUser imUser = imUsers.get(i);
            UserInfo userInfo = new UserInfo()
                .setUid(imUser.getId())
                .setAccount(imUser.getAccount())
                .setNickname(imUser.getNickname())
                .setIsOnline(onlineUsers.get(i));
            container.add(userInfo);
        }
    }

}
