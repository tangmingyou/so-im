package net.sopod.soim.entry.server;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.handler.MessageHandler;
import net.sopod.soim.entry.registry.ProtoMessageHandlerRegistry;
import net.sopod.soim.entry.server.session.Account;
import net.sopod.soim.entry.server.session.NetUser;
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

    public static void dispatch(ImContext ctx, NetUser netUser, MessageLite message) {
        MessageHandler<MessageLite> typeHandler = ProtoMessageHandlerRegistry
                .getTypeHandler(message.getClass());
        if (typeHandler == null) {
            throw new SoimException("no handler for message type: " + message.getClass());
        }
        Worker worker = WorkerGroup.next();
        worker.execute(() -> {
            if (netUser.isAccount()) {
                Account account = (Account) netUser;
                MessageHandlerContext.setAttribute(DubboConstant.CTX_UID, String.valueOf(account.getUid()));
            }
            try {
                typeHandler.exec(ctx, netUser, message);
            } finally {
                MessageHandlerContext.remove();
            }
        });
    }

}
