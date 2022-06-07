package net.sopod.soim.entry.handlers.chat;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.util.Func;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.data.msg.group.Group;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.message.mode.GroupMessage;
import net.sopod.soim.logic.api.message.service.ImGroupChatService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * ReqGroupMessageHandler
 *
 * @author tmy
 * @date 2022-06-07 15:46
 */
@Service
public class ReqGroupMessageHandler extends AccountMessageHandler<Group.ReqGroupMessage> {

    private static final Logger logger = LoggerFactory.getLogger(ReqGroupMessageHandler.class);

    @DubboReference
    private ImGroupChatService imGroupChatService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqGroupMessage req) {
        GroupMessage groupMessage = new GroupMessage()
                .setSender(account.getUid())
                .setGid(req.getGid())
                .setTime(ImClock.millis())
                .setMessage(req.getMessage());
        CompletableFuture<String> future = imGroupChatService.groupMessage(groupMessage);
        future.whenComplete((res, err) -> {
            Group.ResGroupMessage.Builder reqBuilder = Group.ResGroupMessage.newBuilder();
            if (err != null) {
                logger.error("群消息发送失败: {}, {}, {}", req.getGid(), req.getSender(), req.getMessage(), err);
                account.writeNow(ctx, reqBuilder.setSuccess(false)
                        .setMessage(Func.nullSo(err.getMessage(), ""))
                        .build());
                return;
            }
            account.writeNow(ctx, reqBuilder.setSuccess(true)
                    .setMessage(Func.nullSo(res, ""))
                    .build());
        });
        return null;
    }

}
