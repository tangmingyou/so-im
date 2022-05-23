package net.sopod.soim.entry.handlers.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.data.msg.user.UserMsg;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.data.msg.user.UserGroup;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.logic.common.model.UserInfo;
import net.sopod.soim.logic.api.user.service.UserBizService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ReqOnlineUserListHandler
 *
 * @author tmy
 * @date 2022-04-28 11:06
 */
@Service
public class ReqOnlineUserListHandler extends AccountMessageHandler<UserGroup.ReqOnlineUserList> {

    private static final Logger logger = LoggerFactory.getLogger(ReqOnlineUserListHandler.class);

    @DubboReference
    private UserBizService userBizService;

    @Override
    public MessageLite handle(Account account, UserGroup.ReqOnlineUserList msg) {
        List<UserInfo> userInfos = userBizService.onlineUserList(msg.getKeyword());

        List<UserMsg.UserInfo> resUserInfos = userInfos.stream().map(user -> UserMsg.UserInfo.newBuilder()
                .setUid(user.getUid())
                .setAccount(user.getAccount())
                .build())
                .collect(Collectors.toList());

        return UserGroup.ResOnlineUserList.newBuilder()
                .addAllUsers(resUserInfos)
                .build();
    }

}
