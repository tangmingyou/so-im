package net.sopod.soim.entry.handlers.group;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.data.msg.common.Res;
import net.sopod.soim.data.msg.group.Group;
import net.sopod.soim.entry.server.handler.AccountMessageHandler;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.logic.api.group.service.ImGroupService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * ReqCreateGroupHandler
 *
 * @author tmy
 * @date 2022-06-03 10:43
 */
@Service
public class ReqCreateGroupHandler extends AccountMessageHandler<Group.ReqCreateGroup> {

    @DubboReference
    private ImGroupService imGroupService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqCreateGroup req) {
        String groupName = req.getGroupName();
        Res.ResState.Builder resBuilder = Res.ResState.newBuilder();
        try {
            Long groupId = imGroupService.createGroup(account.getUid(), groupName);
            resBuilder.setSuccess(true)
                    .setMessage("群聊创建成功:" + groupId);
        } catch (SoimException e) {
            resBuilder.setSuccess(false)
                    .setMessage(e.getMessage());
        }
        return resBuilder.build();
    }

}
