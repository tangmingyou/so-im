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

    public void routeChatMessage(RouterUser routerUser, String message) {

    }

    public Boolean routeGroupMessage(RouterUser receiverUser, String message) {
        RpcContextUtil.setContextUid(receiverUser.getUid());
        TextChat textChat = new TextChat()
                .setReceiverUid(receiverUser.getUid())
                .setMessage(message)
                .setTime(ImClock.millis())
                .setReceiverName(receiverUser.getAccount());
        return textChatService.sendTextChat(textChat);
    }

}
