package net.sopod.soim.entry.handlers.task;

import com.google.protobuf.MessageLite;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.handler.NetUserMessageHandler;
import net.sopod.soim.entry.server.session.NetUser;
import net.sopod.soim.data.msg.task.Tasks;
import org.springframework.stereotype.Service;

/**
 * NetUserDelayCloseHandler
 *
 * @author tmy
 * @date 2022-04-28 10:19
 */
@Service
public class NetUserDelayCloseHandler extends NetUserMessageHandler<Tasks.NetUserDelayCloseTask> {

    @Override
    public MessageLite handle(ImContext ctx, NetUser netUser, Tasks.NetUserDelayCloseTask msg) {
        if (netUser.isAccount()) {
            return null;
        }
        // 用户还未登录关闭连接
        netUser.channel().close();
        return null;
    }

}
