package net.sopod.soim.entry.handlers.group;

import com.google.protobuf.MessageLite;
import net.sopod.soim.data.msg.group.Group;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.group.model.dto.GroupUserInfo;
import net.sopod.soim.logic.api.group.service.ImGroupService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ReqGroupUsersHandler
 *
 * @author tmy
 * @date 2022-06-05 10:33
 */
@Service
public class ReqGroupUsersHandler extends AccountMessageHandler<Group.ReqGroupUsers> {

    @DubboReference
    private ImGroupService imGroupService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqGroupUsers req) {
        List<GroupUserInfo> groupUserInfos = imGroupService.listGroupUsers(req.getGid());
        List<Group.UserInfo> userInfos = groupUserInfos.stream().map(gu -> Group.UserInfo.newBuilder()
                .setUid(gu.getUid())
                .setAccount(gu.getAccount())
                .setNickname(gu.getNickname())
                .setOnline(Boolean.TRUE.equals(gu.getOnline()))
                .setLastActive(gu.getLastActive())
                .build()
        ).collect(Collectors.toList());
        return Group.ResGroupUsers.newBuilder()
                .addAllUsers(userInfos)
                .build();
    }

}
