package net.sopod.soim.entry.handlers.monitor;

import com.google.protobuf.MessageLite;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.data.msg.monitor.EntryMonitor;
import net.sopod.soim.data.msg.task.Tasks;
import net.sopod.soim.entry.delay.NetUserDelayTaskManager;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.handler.NetUserMessageHandler;
import net.sopod.soim.entry.server.session.Monitor;
import net.sopod.soim.entry.server.session.NetUser;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * ReqMonitorAuthHandler
 *
 * @author tmy
 * @date 2022-06-11 14:30
 */
@Service
public class ReqMonitorAuthHandler extends NetUserMessageHandler<EntryMonitor.ReqMonitorAuth> {

    @Override
    public MessageLite handle(ImContext ctx, NetUser netUser, EntryMonitor.ReqMonitorAuth msg) {
        EntryMonitor.ResMonitorAuth.Builder resBuilder = EntryMonitor.ResMonitorAuth.newBuilder();
        if (!AppConstant.IM_ENTRY_MONITOR_SECURITY.equals(msg.getSecurity())) {
            // 认证失败 6 秒后会关闭连接
            return resBuilder.setSuccess(false).build();
        }
        Monitor monitor = new Monitor(netUser.channel());
        netUser.upgradeMonitor(monitor);
        return resBuilder.setSuccess(true).build();
    }

}
