package net.sopod.soim.logic.message.service;

import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.common.util.StringUtil;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.group.api.model.dto.GroupUser_0;
import net.sopod.soim.das.group.api.service.DasGroupUserService;
import net.sopod.soim.das.message.api.entity.ImGroupMessage;
import net.sopod.soim.das.message.api.service.DasMQMessagePersistentService;
import net.sopod.soim.logic.api.message.service.ImGroupChatService;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.logic.common.model.message.GroupMessage;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import net.sopod.soim.router.api.service.MessageRouteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * ImGroupChatServiceImpl
 *
 * @author tmy
 * @date 2022-06-05 11:14
 */
@DubboService
public class ImGroupChatServiceImpl implements ImGroupChatService {

    @Resource
    private DasMQMessagePersistentService dasMQMessagePersistentService;

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @DubboReference
    private DasGroupUserService dasGroupUserService;

    @DubboReference
    private MessageRouteService messageRouteService;

    /**
     * 序列化群消息
     * 查询群成员列表
     * 分批路由消息
     * 未在线用户记录消息偏移量到das
     */
    @Override
    public CompletableFuture<String> groupMessage(GroupMessage msg) {
        long gMsgId = segmentIdGenerator.nextId(LogicTables.IM_GROUP_MESSAGE);
        // 序列化群消息
        ImGroupMessage imGroupMessage = new ImGroupMessage()
                .setId(gMsgId)
                .setGroupId(msg.getGid())
                .setSender(msg.getSender())
                .setContent(msg.getMessage())
                .setCreateTime(ImClock.date());
        dasMQMessagePersistentService.saveImGroupMessage(imGroupMessage);
        // 查询群成员列表
        List<GroupUser_0> groupUsers = dasGroupUserService.listGroupUsers(msg.getGid());
        Map<Long, GroupUser_0> groupUserMap = Collects.collect2Map(groupUsers, GroupUser_0::getUid);
        // 分批路由消息
        UidConsistentHashSelector<?> uidSelector = UidConsistentHashSelector.getCurrent();
        if (uidSelector == null) {
            List<Long> uidList = groupUsers.stream().map(GroupUser_0::getUid).collect(Collectors.toList());
            List<Boolean> results = messageRouteService.routeGroupMessage(msg.getSender(), uidList, msg.getMessage());
            // TODO 更新未读消息数据偏移量，未读消息条数
            return CompletableFuture.completedFuture("OK");
        }
        Map<?, List<Long>> uidGroups = Collects.group(
                groupUsers,
                user -> uidSelector.select(StringUtil.toString(user.getUid())),
                GroupUser_0::getUid
        );
        // 批量路由消息到对应的 router
        for (List<Long> uidGroup : uidGroups.values()) {
            RpcContextUtil.setContextUid(uidGroup.get(0));
            List<Boolean> results = messageRouteService.routeGroupMessage(msg.getSender(), uidGroup, msg.getMessage());
            Iterator<Long> iterator = uidGroup.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                GroupUser_0 gUser = groupUserMap.get(iterator.next());
                Boolean received = results.get(i);
                // TODO 更新未读消息数据偏移量，未读消息条数

            }
        }
        return CompletableFuture.completedFuture("OK");
    }

}
