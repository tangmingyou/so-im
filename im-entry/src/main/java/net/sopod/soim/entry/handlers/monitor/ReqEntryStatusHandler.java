package net.sopod.soim.entry.handlers.monitor;

import com.google.protobuf.MessageLite;
import lombok.AllArgsConstructor;
import net.sopod.soim.common.util.ImClock;
import net.sopod.soim.data.msg.monitor.EntryMonitor;
import net.sopod.soim.entry.server.AccountRegistry;
import net.sopod.soim.entry.server.handler.ImContext;
import net.sopod.soim.entry.server.handler.MonitorMessageHandler;
import net.sopod.soim.entry.server.session.Monitor;
import org.springframework.stereotype.Service;

/**
 * ReqEntryStatusHandler
 *
 * @author tmy
 * @date 2022-06-11 14:39
 */
@Service
@AllArgsConstructor
public class ReqEntryStatusHandler extends MonitorMessageHandler<EntryMonitor.ReqEntryStatus> {

    private final AccountRegistry accountRegistry;

    @Override
    public MessageLite handle(ImContext ctx, Monitor monitor, EntryMonitor.ReqEntryStatus req) {
        return EntryMonitor.ResEntryStatus.newBuilder()
                .setConnections(accountRegistry.getRegistryAccountSize())
                .setStatusTime(ImClock.millis())
                .build();
    }

}
