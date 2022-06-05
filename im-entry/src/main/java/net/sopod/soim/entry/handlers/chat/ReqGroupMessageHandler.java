package net.sopod.soim.entry.handlers.chat;

import com.google.protobuf.MessageLite;
import net.sopod.soim.data.msg.group.Group;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import org.springframework.stereotype.Service;

/**
 * ReqGroupMessageHandler
 *
 * @author tmy
 * @date 2022-06-05 11:05
 */
@Service
public class ReqGroupMessageHandler extends AccountMessageHandler<Group.ReqGroupMessage> {

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqGroupMessage req) {
        return null;
    }

}
