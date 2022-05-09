package net.sopod.soim.router.datasync.server.session;

import net.sopod.soim.router.datasync.server.SyncClient;

import java.util.concurrent.ConcurrentHashMap;

/**
 * SyncClientSession
 *
 * @author tmy
 * @date 2022-05-09 17:45
 */
public class SyncClientSession {

    private static final SyncClientSession INSTANCE = new SyncClientSession();

    public static SyncClientSession getInstance() {
        return INSTANCE;
    }

    /**
     * 服务端addr，连接 channel
     */
    public final ConcurrentHashMap<String, SyncClient> serverChannel = new ConcurrentHashMap<>();

    public void connect(String host, int port) {
        SyncClient client = new SyncClient();
        try {
            client.connect(host, port);
            serverChannel.put(host + ":" + port, client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close(String addr) {
        SyncClient syncClient = serverChannel.get(addr);
        if (syncClient != null) {
            syncClient.close();
        }
    }

}
