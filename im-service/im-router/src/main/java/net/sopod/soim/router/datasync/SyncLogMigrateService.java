package net.sopod.soim.router.datasync;

import com.google.common.base.Preconditions;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import net.sopod.soim.router.config.ImRouterAppContext;
import net.sopod.soim.router.datasync.server.SyncClient;
import net.sopod.soim.router.datasync.server.data.SyncCmd;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * SyncLogMigrateService
 * 新节点集群数据迁移
 *
 * @author tmy
 * @date 2022-05-08 09:42
 */
@Service
public class SyncLogMigrateService {

    private static final Logger logger = LoggerFactory.getLogger(SyncLogMigrateService.class);

    private volatile List<Pair<String, Integer>> migrateHosts;
    private LinkedList<Pair<String, Integer>> curMigrateHosts;

    private List<SyncClient> migrateClients = new ArrayList<>();

    private SyncClient curClient;

    /**
     * 同步数据
     * @param migrateHosts 同步数据节点
     */
    public void migrateSyncLog(List<Pair<String, Integer>> migrateHosts) {
        Preconditions.checkState(this.migrateHosts == null, "当前正在进行数据同步");
        this.migrateHosts = migrateHosts;
        this.curMigrateHosts = new LinkedList<>(migrateHosts);
        logger.info("开始连接{}节点同步服务...", AppConstant.APP_IM_ROUTER_NAME);
        this.syncNextHost();
    }

    /**
     *
     * @return 是否还有下一个同步数据节点
     */
    public synchronized boolean syncNextHost() {
        if (this.curClient != null) {
            // TODO 优化多节点同时同步数据
            throw new IllegalStateException("当前有客户端正在全量同步数据");
        }
        if (this.curMigrateHosts.isEmpty()) {
            // 同步结束
            this.allHostSyncFinish();
            return true;
        }
        Pair<String, Integer> nextHost = this.curMigrateHosts.removeFirst();
        try {
            this.curClient = new SyncClient();
            this.curClient.connect(nextHost.getLeft(), nextHost.getRight());
            this.curClient.syncLogByHash(ImRouterAppContext.getAppAddr());
        } catch (InterruptedException e) {
            logger.error("节点{}:{}连接失败, 跳过!", nextHost.getLeft(), nextHost.getRight(), e);
            // 同步下一个节点
            return this.syncNextHost();
        }
        return true;
    }

    public synchronized void curHostSyncEnd() {
        if (this.curClient == null) {
            logger.warn("当前无正在全量同步数据的客户端");
            return;
        }
        this.migrateClients.add(this.curClient);
        this.curClient = null;
        this.syncNextHost();
    }

    /**
     * 所有节点全量同步完成
     * 注册服务
     * 接受后续日志数据
     * n秒无日志数据后，发送消息可移除数据
     * 断开连接
     */
    private void allHostSyncFinish() {
        logger.info("所有节点数据同步完成：执行注册服务....");
        ImRouterAppContext.doRegistry();
        logger.info("注册服务成功");
        // 通知服务端同步结束，关闭服务端连接
        for (SyncClient migrateClient : migrateClients) {
            SyncCmd syncFinishCmd = new SyncCmd();
            syncFinishCmd.setCmdType(SyncCmd.SYNC_FINISH_CLOSE);
            migrateClient.write(syncFinishCmd);
        }
        for (SyncClient migratedClient : migrateClients) {
            migratedClient.close();
        }
    }

    @Deprecated
    private void fullSync() {
        Map<Long, RouterUser> routerUserMap = RouterUserStorage.getInstance().getRouterUserMap();
        for (Map.Entry<Long, RouterUser> entry : routerUserMap.entrySet()) {
            Long key = entry.getKey();
            RouterUser value = entry.getValue();
            Jackson.json().serialize(value);
        }
    }

}
