package net.sopod.soim.router.datasync.server.session;

import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.router.datasync.server.SyncClient;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SyncClientSession
 *
 * @author tmy
 * @date 2022-05-09 17:45
 */
@Deprecated
public class SyncClientSession {

    private static final Logger logger = LoggerFactory.getLogger(SyncClientSession.class);

    private static final SyncClientSession INSTANCE = new SyncClientSession();

    public static SyncClientSession getInstance() {
        return INSTANCE;
    }

    /**
     * 服务端addr，连接 channel
     */
    public final ConcurrentHashMap<String, SyncClient> syncLogServers = new ConcurrentHashMap<>();

    public void connect(String host, int port) {
        SyncClient client = new SyncClient();
        try {
            client.connect(host, port);
            syncLogServers.put(host + ":" + port, client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close(String addr) {
        SyncClient syncClient = syncLogServers.get(addr);
        if (syncClient != null) {
            syncClient.close();
        }
    }

}
