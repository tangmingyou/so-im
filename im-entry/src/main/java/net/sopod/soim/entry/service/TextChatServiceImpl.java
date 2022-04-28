package net.sopod.soim.entry.service;

import net.sopod.soim.core.session.Account;
import net.sopod.soim.data.msg.chat.Chat;
import net.sopod.soim.entry.api.service.TextChatService;
import net.sopod.soim.entry.server.AccountRegistry;
import net.sopod.soim.logic.common.model.TextChat;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * TextChatServiceImpl
 *
 * @author tmy
 * @date 2022-04-28 14:43
 */
@DubboService
public class TextChatServiceImpl implements TextChatService {

    @Resource
    private AccountRegistry accountRegistry;

    @Override
    public Boolean sendTextChat(TextChat chat) {
        Long receiverUid = chat.getReceiverUid();
        Account account = accountRegistry.get(receiverUid);
        if (account == null) {
            return Boolean.FALSE;
        }
        Chat.TextChat resTextChat = Chat.TextChat.newBuilder()
                .setSender(chat.getUid())
                .setReceiver(chat.getReceiverUid())
                .setReceiverAccount(chat.getReceiverName())
                .setMessage(chat.getMessage())
                .setTime(chat.getTime())
                .build();
        account.channel().writeAndFlush(resTextChat);
        return Boolean.TRUE;
    }

}