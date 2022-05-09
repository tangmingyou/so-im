package net.sopod.soim.router.datasync.server.session;

import net.sopod.soim.router.datasync.server.data.SyncCmd;
import net.sopod.soim.router.datasync.server.SyncServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SyncSession
 * 作为客户端或服务端主动操作连接
 * 服务端场景：
 *   1.获取客户端连接(备份服务器类型，新增服务器同步类型)
 * 客户端场景：
 *   1.即将删除：推送到其他几个节点服务器
 *   2.新增节点：连接几个节点服务器发送拉取数据命令
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
