package net.sopod.soim.client.handler.msg;

import com.google.inject.Singleton;
import net.sopod.soim.client.logger.Logger;
import net.sopod.soim.client.session.MessageHandler;
import net.sopod.soim.data.msg.chat.Chat;

/**
 * TextChatHandler
 *
 * @author tmy
 * @date 2022-04-28 16:25
 */
@Singleton
public class TextChatHandler implements MessageHandler<Chat.TextChat> {

    @Override
    public void handleMsg(Chat.TextChat res) {
        Logger.info("{}: {}", res.getSender(), res.getMessage());
    }

}
