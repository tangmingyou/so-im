package net.sopod.soim.entry.api.service;

import net.sopod.soim.logic.common.model.TextChat;

/**
 * TextChatService
 *
 * @author tmy
 * @date 2022-04-28 14:42
 */
public interface TextChatService {

    Boolean sendTextChat(TextChat textChat);

}
