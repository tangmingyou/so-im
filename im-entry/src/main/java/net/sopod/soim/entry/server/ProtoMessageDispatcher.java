package net.sopod.soim.entry.server;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.core.handler.MessageHandler;
import net.sopod.soim.core.registry.ProtoMessageHandlerRegistry;
import net.sopod.soim.core.session.Account;
import net.sopod.soim.core.session.NetUser;
import net.sopod.soim.entry.config.MessageHandlerContext;
import net.sopod.soim.entry.worker.Worker;
import net.sopod.soim.entry.worker.WorkerGroup;

/**
 * ProtoMessageDispatcher
 *
 * @author tmy
 * @date 2022-04-13 17:21
 */
public class ProtoMessageDispatcher {

    public static void dispatch(NetUser netUser, MessageLite message) {
        MessageHandler<MessageLite> typeHandler = ProtoMessageHandlerRegistry
                .getTypeHandler(message.getClass());
        if (typeHandler == null) {
            throw new IllegalCallerException("no handler for message : " + message.getClass());
        }
        Worker worker = WorkerGroup.next();
        worker.execute(() -> {
            if (netUser.isAccount()) {
                Account account = (Account)netUser;
                MessageHandlerContext.setAttribute(DubboConstant.CTX_UID, String.valueOf(account.getUid()));
            }
            try {
                typeHandler.exec(netUser, message);
            } finally {
                MessageHandlerContext.remove();
            }
        });
    }

}
