package net.sopod.soim.router.datasync.server.session;

import org.apache.dubbo.common.utils.ConcurrentHashSet;

import java.nio.channels.Channel;
import java.util.Set;

/**
 * SyncServerSession
 *
 * @author tmy
 * @date 2022-05-09 14:49
 */
public class SyncServerSession {

    private static final SyncServerSession INSTANCE = new SyncServerSession();

    public static SyncServerSession getInstance() {
        return INSTANCE;
    }

    /** 已连接客户端 */
    public final Set<Channel> clients = new ConcurrentHashSet<>();

    /** 备份数据节点 */
    public final Set<Channel> backupClients = new ConcurrentHashSet<>();

    /** 新增节点发送 Sync 命令后添加到该连接集合 */
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

}
