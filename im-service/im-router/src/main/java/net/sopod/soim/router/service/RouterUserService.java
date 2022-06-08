package net.sopod.soim.router.service;

import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.entry.api.service.TextChatService;
import net.sopod.soim.logic.common.model.TextChat;
import net.sopod.soim.logic.common.util.RpcContextUtil;
import net.sopod.soim.router.cache.RouterUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * RouterUserService
 *
 * @author tmy
 * @date 2022-06-06 9:34
 */
@Service
public class RouterUserService {

    @DubboReference
    private TextChatService textChatService;

    public Boolean routeChatMessage(Long sender, RouterUser receiverUser, String message) {
        RpcContextUtil.setContextUid(receiverUser.getUid());
        TextChat textChat = new TextChat()
                .setSenderUid(sender)
                .setReceiverUid(receiverUser.getUid())
                .setMessage(message)
                .setTime(ImClock.millis())
                .setReceiverName(receiverUser.getAccount());
        return textChatService.sendTextChat(textChat);
    }

    public Boolean routeGroupMessage(Long sender, RouterUser receiverUser, String message) {
        TextChat textChat = new TextChat()
                .setSenderUid(sender)
                .setReceiverUid(receiverUser.getUid())
                .setMessage(message)
                .setTime(ImClock.millis())
                .setReceiverName(receiverUser.getAccount());
        RpcContextUtil.setContextUid(receiverUser.getUid());
        // TODO 单独群消息接口
        return textChatService.sendTextChat(textChat);
    }

}
