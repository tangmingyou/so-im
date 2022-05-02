package net.sopod.soim.logic.user.service;

import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.das.user.api.model.entity.ImUser;
import net.sopod.soim.das.user.api.service.UserDasService;
import net.sopod.soim.logic.common.model.TextChat;
import net.sopod.soim.router.api.service.UserEntryRegistryService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Objects;

/**
 * ChatServiceImpl
 *
 * @author tmy
 * @date 2022-04-28 14:22
 */
@DubboService
public class ChatServiceImpl implements ChatService {

    @DubboReference
    private UserEntryRegistryService userEntryRegistryService;

    @DubboReference
    private UserDasService userDasService;

    @Override
    public Boolean textChat(TextChat textChat) {
        if (textChat.getReceiverUid() == null
                || Objects.equals(textChat.getReceiverUid(), 0L)) {
            ImUser receiverUser = userDasService.getNormalUserByAccount(textChat.getReceiverName());
            if (receiverUser == null) {
                return false;
            }
            textChat.setReceiverUid(receiverUser.getId());
        }
        RpcContext.getServiceContext().setAttachment(DubboConstant.CTX_UID, String.valueOf(textChat.getReceiverUid()));
        return userEntryRegistryService.routeTextChat(textChat);
    }

}
