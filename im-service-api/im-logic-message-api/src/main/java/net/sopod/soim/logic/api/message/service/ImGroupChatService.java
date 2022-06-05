package net.sopod.soim.logic.api.message.service;

import net.sopod.soim.logic.api.message.mode.GroupMessage;

import java.util.concurrent.CompletableFuture;

/**
 * ImGroupChatService
 *
 * @author tmy
 * @date 2022-06-05 11:09
 */
public interface ImGroupChatService {

    CompletableFuture<String> groupMessage(GroupMessage groupMessage);

}
