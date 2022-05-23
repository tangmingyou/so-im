package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.data.msg.user.Friend;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.user.service.FriendService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * ReqFriendListHandler
 *
 * @author tmy
 * @date 2022-05-23 17:42
 */
@Service
public class ReqFriendListHandler extends AccountMessageHandler<Friend.ReqFriendList> {

    @DubboReference
    private FriendService friendService;

    @Override
    public MessageLite handle(Account account, Friend.ReqFriendList req) {

        return null;
    }

}
