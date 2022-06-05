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
 * ReqJoinGroupHandler
 *
 * @author tmy
 * @date 2022-06-05 10:28
 */
@Service
public class ReqJoinGroupHandler extends AccountMessageHandler<Group.ReqJoinGroup> {

    @DubboReference
    private ImGroupService imGroupService;

    @Override
    public MessageLite handle(ImContext ctx, Account account, Group.ReqJoinGroup req) {
        long groupId = req.getGid();
        Res.ResState.Builder resBuilder = Res.ResState.newBuilder();
        try {
            Long joinId = imGroupService.joinGroup(groupId, account.getUid());
            resBuilder.setSuccess(true)
                    .setMessage("加入群聊成功:" + joinId);
        } catch (SoimException e) {
            resBuilder.setSuccess(false)
                    .setMessage(e.getMessage());
        }
        return resBuilder.build();
    }

}
