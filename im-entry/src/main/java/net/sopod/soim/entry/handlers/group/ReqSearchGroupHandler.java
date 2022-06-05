package net.sopod.soim.entry.handlers.group;

import com.google.protobuf.MessageLite;
import net.sopod.soim.das.group.api.model.entity.ImGroup;
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
 * ReqSearchGroupHandler
 *
 * @author tmy
 * @date 2022-06-05 09:20
 */
@Service
public class ReqSearchGroupHandler extends AccountMessageHandler<Group.ReqSearchGroup> {

    @DubboReference
    private ImGroupService imGroupService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqSearchGroup req) {
        String groupName = req.getGroupName();
        List<ImGroup> imGroups = imGroupService.searchGroup(groupName);
        List<Group.GroupInfo> groupInfos = imGroups.stream().map(imGroup -> Group.GroupInfo.newBuilder()
                .setGid(imGroup.getId())
                .setGroupName(imGroup.getGroupName())
                .setUserLimit(imGroup.getUserLimit())
                .setUserNum(imGroup.getUserNum())
                .setCreateTime(imGroup.getCreateTime().getTime())
                .build()
        ).collect(Collectors.toList());
        return Group.ResSearchGroup.newBuilder().addAllGroups(groupInfos).build();
    }

}
