package net.sopod.soim.entry.handlers.chat;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.data.msg.chat.Chat;
import net.sopod.soim.logic.common.model.TextChat;
import net.sopod.soim.logic.user.service.ChatService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * ReqTextChatHandler
 *
 * @author tmy
 * @date 2022-04-28 14:15
 */
@Service
public class ReqTextChatHandler extends AccountMessageHandler<Chat.TextChat> {

    private static final Logger logger = LoggerFactory.getLogger(ReqTextChatHandler.class);

    @DubboReference
    private ChatService chatService;

    @Override
    public MessageLite handle(Account account, Chat.TextChat msg) {
        TextChat textChat = new TextChat()
                .setUid(msg.getSender())
                .setReceiverUid(msg.getReceiver())
                .setReceiverName(msg.getReceiverAccount())
                .setTime(msg.getTime())
                .setMessage(msg.getMessage());
        Boolean result = chatService.textChat(textChat);
        logger.info("发送结果: {}", result);
        return null;
    }

}
