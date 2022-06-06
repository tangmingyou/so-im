package net.sopod.soim.entry.handlers.group;

import com.google.protobuf.MessageLite;
import net.sopod.soim.das.group.api.model.dto.GroupView;
import net.sopod.soim.data.msg.group.Group;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.group.service.ImGroupService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ReqUserGroupsHandler
 *
 * @author tmy
 * @date 2022-06-06 21:18
 */
@Service
public class ReqUserGroupsHandler extends AccountMessageHandler<Group.ReqUserGroups> {

    @DubboReference
    private ImGroupService imGroupService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqUserGroups req) {
        List<GroupView> groupViews = imGroupService.listUserGroups(account.getUid());
        List<Group.GroupInfo> groupInfos = groupViews.stream().map(g -> Group.GroupInfo.newBuilder()
                .setGid(g.getGid())
                .setGroupName(g.getGName())
                .setUserLimit(g.getUserLimit())
                .setUserNum(g.getUserNum())
                .setMasterUid(g.getMasterUid())
                .setCreateTime(g.getCreateTime() == null ? 0L : g.getCreateTime().getTime())
                .build()
        ).collect(Collectors.toList());

        return Group.ResUserGroups.newBuilder()
                .addAllGroups(groupInfos)
                .build();
    }

}
