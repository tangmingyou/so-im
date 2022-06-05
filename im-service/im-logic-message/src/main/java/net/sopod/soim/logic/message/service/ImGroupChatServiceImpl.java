package net.sopod.soim.logic.message.service;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.group.api.model.dto.GroupUser_0;
import net.sopod.soim.das.group.api.service.DasGroupUserService;
import net.sopod.soim.das.message.api.entity.ImGroupMessage;
import net.sopod.soim.das.message.api.service.DasMQMessagePersistentService;
import net.sopod.soim.logic.api.message.mode.GroupMessage;
import net.sopod.soim.logic.api.message.service.ImGroupChatService;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        // 分批路由消息
        UidConsistentHashSelector<?> uidSelector = UidConsistentHashSelector.getCurrent();


        return null;
    }

}
