package net.sopod.soim.entry.handlers.chat;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.data.msg.chat.Chat;
import net.sopod.soim.entry.worker.FutureExecutor;
import net.sopod.soim.logic.api.message.service.ImUserChatService;
import net.sopod.soim.logic.common.model.message.UserMessage;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

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
    private ImUserChatService imUserChatService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Chat.TextChat msg) {
        UserMessage textChat = new UserMessage()
                .setSenderUid(msg.getSender())
                .setReceiverUid(msg.getReceiver())
                .setTime(msg.getTime())
                .setMessage(msg.getMessage());
        CompletableFuture<String> future = imUserChatService.userMessage(textChat);
        future.whenCompleteAsync((res, e) -> {
            if (e != null) {
                logger.error("发送失败:", e);
                return;
            }
            logger.info("发送结果: {}", res);
        }, FutureExecutor.getInstance());
        return null;
    }

}
