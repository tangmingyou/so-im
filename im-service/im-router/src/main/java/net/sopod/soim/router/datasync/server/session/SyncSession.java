package net.sopod.soim.router.datasync.server.session;

import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.SyncServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SyncSession
 * 作为客户端或服务端主动操作连接
 *
 * @author tmy
 * @date 2022-05-08 17:34
 */
public class SyncSession {
    private static final Logger logger = LoggerFactory.getLogger(SyncSession.class);

    public static SyncSession client() {
        return null;
    }

    public static SyncSession server() {

        return null;
    }

    public static class SyncSessionServer {

        private final SyncServer syncServer;

        public SyncSessionServer(int port) {
            this.syncServer = new SyncServer();
            this.syncServer.start(9999, err -> {
                logger.error("sync server start fail!", err);
            });
        }

        public void writeTo() {

        }

    }

    public static class SyncSessionClient {

        public SyncSessionClient() {

        }

        public void write(SyncCmd syncCmd) {

        }

    }

}
