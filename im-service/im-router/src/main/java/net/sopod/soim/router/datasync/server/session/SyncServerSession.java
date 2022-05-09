package net.sopod.soim.router.datasync.server.session;

import net.sopod.soim.router.datasync.server.SyncServer;
import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.Channel;
import java.util.Set;

/**
 * SyncServerSession
 * 作为客户端或服务端主动操作连接
 * 服务端场景：
 *   1.获取客户端连接(备份服务器类型，新增服务器同步类型)
 * 客户端场景：
 *   1.即将删除：推送到其他几个节点服务器
 *   2.新增节点：连接几个节点服务器发送拉取数据命令
 *
 * @author tmy
 * @date 2022-05-09 14:49
 */
public class SyncServerSession {

    private static final Logger logger = LoggerFactory.getLogger(SyncServerSession.class);

    private static final SyncServerSession INSTANCE = new SyncServerSession();

    public static SyncServerSession getInstance() {
        return INSTANCE;
    }

    private SyncServer syncServer;

    /**
     * 已连接客户端
     */
    public final Set<Channel> clients = new ConcurrentHashSet<>();

    /**
     * 备份数据节点
     */
    public final Set<Channel> backupClients = new ConcurrentHashSet<>();

    /**
     * 新增节点发送 Sync 命令后添加到该连接集合
     */
    public final Set<Channel> newNodeClients = new ConcurrentHashSet<>();

    public void addClient(Channel channel) {
        clients.add(channel);
    }

    public void addBackupClient() {

    }

    public void removeClient(Channel channel) {
        clients.remove(channel);
        backupClients.remove(channel);
        newNodeClients.remove(channel);
    }

    public void start(int port) {
        this.syncServer = new SyncServer();
        syncServer.start(port, err -> {
            throw new IllegalStateException("备份服务启动失败", err);
        });

    }

    public void close() {
        this.syncServer.close();
    }

}
