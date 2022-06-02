package net.sopod.soim.logic.user.service;

import net.sopod.soim.common.dubbo.exception.LogicException;
import net.sopod.soim.common.dubbo.exception.ServiceException;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.FriendDas;
import net.sopod.soim.das.user.api.service.UserDas;
import net.sopod.soim.logic.api.user.service.ChatService;
import net.sopod.soim.logic.common.model.TextChat;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.api.service.UserRouteService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * ChatServiceImpl
 *
 * @author tmy
 * @date 2022-04-28 14:22
 */
@DubboService
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @DubboReference
    private UserRouteService userRouteService;

    @DubboReference
    private UserDas userDas;

    @DubboReference
    private FriendDas friendDas;

    @Override
    public Boolean textChat(TextChat textChat) {
        if (textChat.getReceiverUid() == null
                || Objects.equals(textChat.getReceiverUid(), 0L)) {
            ImUser receiverUser = userDas.getNormalUserByAccount(textChat.getReceiverName());
            if (receiverUser == null) {
                // TODO receiverUid 由客户端传递
                throw new LogicException("聊天对象不存在");
            }
            textChat.setReceiverUid(receiverUser.getId());
        }
        // 查询好友关系
        Long friendId = friendDas.getFriendId(textChat.getUid(), textChat.getReceiverUid());
        // 不是好友
        if (friendId == null) {
            throw new LogicException("请添加好友后发送消息");
        }
        // 设置调用 router 为消息接受者地址
        RpcContextUtil.setContextUid(textChat.getReceiverUid());
        return userRouteService.routeTextChat(friendId, textChat);
    }

}
