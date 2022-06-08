package net.sopod.soim.logic.api.message.service;

import net.sopod.soim.logic.common.model.message.UserMessage;

import java.util.concurrent.CompletableFuture;

/**
 * ImUserChatService
 *
 * @author tmy
 * @date 2022-06-08 17:19
 */
public interface ImUserChatService {

    CompletableFuture<String> userMessage(UserMessage msg);

}
