package net.sopod.soim.entry.handler.user;

import com.google.protobuf.MessageLite;
import net.sopod.soim.core.handler.AccountMessageHandler;
import net.sopod.soim.core.session.Account;
import net.sopod.soim.data.msg.user.UserGroup;
import net.sopod.soim.logic.user.service.UserService;
import net.sopod.soim.logic.common.model.UserInfo;
import org.apache.dubbo.config.annotation.DubboReference;
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

    @DubboReference
    private UserService userService;

    @Override
    public MessageLite handle(Account account, UserGroup.ReqOnlineUserList msg) {
        List<UserInfo> userInfos = userService.onlineUserList(msg.getKeyword());
        List<UserGroup.UserInfo> resUserInfos = userInfos.stream().map(user -> UserGroup.UserInfo.newBuilder()
                .setUid(user.getUid())
                .setAccount(user.getAccount())
                .build())
                .collect(Collectors.toList());

        return UserGroup.ResOnlineUserList.newBuilder()
                .addAllUsers(resUserInfos)
                .build();
    }

}
