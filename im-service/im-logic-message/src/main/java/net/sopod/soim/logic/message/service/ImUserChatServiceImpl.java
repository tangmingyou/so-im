package net.sopod.soim.logic.message.service;

import net.sopod.soim.common.dubbo.exception.LogicException;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.das.common.config.LogicTables;
import net.sopod.soim.das.message.api.entity.ImMessage;
import net.sopod.soim.das.message.api.service.DasMQMessagePersistentService;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.message.service.ImUserChatService;
import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.logic.common.model.message.UserMessage;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.api.service.MessageRouteService;
import net.sopod.soim.router.api.service.UserRouteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * ImUserChatServiceImpl
 * 单聊消息
 *
 * @author tmy
 * @date 2022-06-08 14:59
 */
@DubboService
public class ImUserChatServiceImpl implements ImUserChatService {

    private static final Logger logger = LoggerFactory.getLogger(ImUserChatServiceImpl.class);

    @DubboReference
    private UserRouteService userRouteService;

    @DubboReference
    private MessageRouteService messageRouteService;

    @DubboReference
    private UserDas userDas;

    @DubboReference
    private FriendDas friendDas;

    @Resource
    private SegmentIdGenerator segmentIdGenerator;

    @Resource
    private DasMQMessagePersistentService dasMQMessagePersistentService;

    @Override
    public CompletableFuture<String> userMessage(UserMessage msg) {
//        if (msg.getReceiverUid() == null
//                || Objects.equals(msg.getReceiverUid(), 0L)) {
//            ImUser receiverUser = userDas.getNormalUserByAccount(msg.getReceiverName());
//            if (receiverUser == null) {
//                throw new LogicException("聊天对象不存在");
//            }
//            msg.setReceiverUid(receiverUser.getId());
//        }
        // receiverUid 由客户端传递
        // 查询好友关系
        Long relationId = friendDas.getRelationId(msg.getSenderUid(), msg.getReceiverUid());
        // 不是好友
        if (relationId == null) {
            throw new LogicException("请添加对方好友后发送消息");
        }
        // 通过消息队列持久化存储到db
        ImMessage imMessage = new ImMessage()
                .setRelationId(relationId)
                .setId(segmentIdGenerator.nextId(LogicTables.IM_MESSAGE))
                .setContent(msg.getMessage())
                .setSender(msg.getSenderUid())
                .setReceiver(msg.getReceiverUid())
                .setCreateTime(ImClock.date());
        dasMQMessagePersistentService.saveImMessage(imMessage);
        // 设置调用 router 为消息接受者地址
        RpcContextUtil.setContextUid(msg.getReceiverUid());
        try {
            Boolean success = messageRouteService.routeUserMessage(msg);
            return CompletableFuture.completedFuture(Boolean.TRUE.equals(success) ? null : "消息未接受");
        } catch (SoimException e) {
            return CompletableFuture.failedFuture(e);
        }
    }

}
