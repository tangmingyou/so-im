package net.sopod.soim.router.datasync;

import com.google.common.base.Preconditions;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.util.Jackson;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import net.sopod.soim.router.config.AppContextHolder;
import net.sopod.soim.router.datasync.server.SyncClient;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    /**
     * 同步数据
     * @param migrateHosts 同步数据节点
     */
    public void migrateSyncLog(List<Pair<String, Integer>> migrateHosts) {
        Preconditions.checkState(migrateHosts != null, "当前正在进行数据同步");
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
        if (this.curMigrateHosts.isEmpty()) {
            return false;
        }
        Pair<String, Integer> nextHost = this.curMigrateHosts.removeFirst();
        try {
            SyncClient client = new SyncClient();
            client.connect(nextHost.getLeft(), nextHost.getRight());
            client.syncLogByHash(AppContextHolder.getAppAddr());
        } catch (InterruptedException e) {
            logger.error("节点{}:{}连接失败, 跳过!", nextHost.getLeft(), nextHost.getRight(), e);
            // 同步下一个节点
            return this.syncNextHost();
        }
        return true;
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

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "A");
        map.put("2", "B");
        Iterator<String> iterator = map.values().iterator();
        System.out.println("a." + iterator.next());
        map.remove("2");
        System.out.println("b." + iterator.next());
        System.out.println(iterator.next());
    }

}
