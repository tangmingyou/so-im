package net.sopod.soim.router.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import net.sopod.soim.router.cache.RouterUser;
import net.sopod.soim.router.cache.RouterUserStorage;
import net.sopod.soim.router.datasync.SyncLogMigrateService;
import net.sopod.soim.router.datasync.server.session.SyncServerSession;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.support.RegistryManager;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.spring.boot.context.event.AwaitingNonWebApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * AppcationInitialed
 * 参考 dubbo {@link AwaitingNonWebApplicationListener}
 *
 * @author tmy
 * @date 2022-05-04 09:51
 */
@Configuration
public class ImRouterAppOnReady implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ImRouterAppOnReady.class);

    /**
     * 同步一致性hash临近节点数据
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ImRouterAppContext.setApplicationContext(event.getApplicationContext());
        this.init();

        // 启动数据同步服务
        this.startSyncServer();

        SyncLogMigrateService syncLogMigrateService = event.getApplicationContext().getBean(SyncLogMigrateService.class);
        // 检查集群状态
        boolean registryNow = this.checkClusterEnvironment(syncLogMigrateService);

        // 服务已可用立即进行注册
        if (registryNow) {
            // this.createMockData();
            ImRouterAppContext.doRegistry();
        }
    }

    private AtomicLong uidCounter = new AtomicLong(10000);

    private void createMockData() {
        for (long i = 10000L; i < 11000L; i++) {
            RouterUser routerUser = new RouterUser()
                    .setUid(i)
                    .setAccount("京城" + i)
                    .setIsOnline(i % 2 == 0)
                    .setImEntryAddr("127.0.0.1:1313");
            RouterUserStorage.getInstance().put(i, routerUser);
        }

        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(() -> {
                    for (int i = 0; i < 10; i++) {
                        RouterUser routerUser = RouterUserStorage.getInstance().get(uidCounter.getAndIncrement());
                        if (routerUser == null) {
                            return;
                        }
                        routerUser.setAccount("changeAccount:" + uidCounter.get());
                    }
                    logger.info("change log....: {}", uidCounter.get());
                }, 30, 10, TimeUnit.SECONDS);
    }

    /**
     * 获取注册中心地址
     */
    private void init() {
        RegistryManager registryManager = ApplicationModel.defaultModel().getBeanFactory()
                .getBean(RegistryManager.class);
        Collection<Registry> registries = registryManager.getRegistries();
        if (Collects.isNotEmpty(registries)) {
            for (Registry registry : registries) {
                // isServiceDiscovery(): true是注册应用(im-router)的registry, false是注册服务接口的registry
                logger.info("registry: {}, {}", registry.getUrl().getAddress(), registry.isServiceDiscovery());
                if (registry.isAvailable()
                        && registry.isServiceDiscovery()) {
                    String discoveryAddr = registry.getUrl().getAddress();
                    ImRouterAppContext.setServiceDiscoveryRegistryAddr(discoveryAddr);
                    logger.info("service discovery registry addr: {}", discoveryAddr);
                    break;
                }
            }
        }
        if (ImRouterAppContext.getDiscoveryAddr() == null) {
            logger.info("未解析到服务注册中心: 将直接启动不进行数据迁移");
        }
    }

    private void startSyncServer() {
        SyncServerSession.getInstance()
                .start(ImRouterAppContext.getAppPort() + AppConstant.IM_ROUTER_SYNC_SERVER_OFFSET);
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    /**
     * 检查集群情况
     * @return 是否可立即注册服务
     */
    public boolean checkClusterEnvironment(SyncLogMigrateService syncLogMigrateService) {
        // TODO 加分布式锁，同一时间单一节点进行数据同步

        List<Instance> clusterImEntryInstance = ImRouterAppContext.getClusterImRouterInstance();
        if (Collects.isEmpty(clusterImEntryInstance)) {
            logger.info("当前集群无{}节点，直接启动", AppConstant.APP_IM_ROUTER_NAME);
            return true;
        }
        logger.info("当前集群{}节点: {} of {}",
                AppConstant.APP_IM_ROUTER_NAME,
                clusterImEntryInstance.size(),
                clusterImEntryInstance.stream().map(Instance::toInetAddr).collect(Collectors.toList()));

        // 构建一致性 hash 环，计算需要同步数据的节点
        Map<String, Instance> addrInstanceMap = Collects.collect2Map(clusterImEntryInstance,
                Instance::toInetAddr, // 服务地址, 如: 192.168.56.1:3031
                new HashMap<>(Collects.mapCapacity(clusterImEntryInstance.size()))
        );
        UidConsistentHashSelector<Instance> selector = new UidConsistentHashSelector<>(addrInstanceMap, addrInstanceMap.hashCode());
        Set<Instance> migrateNodes = selector.selectMigrateNodes(ImRouterAppContext.getAppAddr());
        logger.info("需迁移数据{}节点: {} of {}",
                AppConstant.APP_IM_ROUTER_NAME,
                migrateNodes.size(),
                migrateNodes.stream().map(Instance::toInetAddr).collect(Collectors.toList()));
        if (Collects.isEmpty(migrateNodes)) {
            return true;
        }
        // 发起客户端连接，开始同步数据
        List<Pair<String, Integer>> migrateHosts = migrateNodes.stream()
                .map(instance -> ImmutablePair.of(instance.getIp(), instance.getPort() + AppConstant.IM_ROUTER_SYNC_SERVER_OFFSET))
                .collect(Collectors.toList());
        // 开始同步数据
        syncLogMigrateService.migrateSyncLog(migrateHosts);
        return false;
    }

    /**
     * 一致性hash，使用了虚拟节点会导致迁移多个数据节点
     *
     * 应用启动后，进行新增 im-router 节点逻辑处理
     * 0.获取分布式全局锁，成功开始同步数据(TODO nacos不支持，想其他办法)
     * 1.获取现有所有 im-router 节点，加上当前节点，生成新的一致性hash虚拟节点表
     * 2.计算所有需要迁移数据的节点，当前虚拟节点的顺时针临近节点
     * 3.依次调用数据迁移节点，分页拉取router缓存用户数据（如有数据节点获取失败，探测是否可用，不可用从第1步重新开始；）
     *   如已拉取数据有更新需要双写？？[CGLib], 服务注册前需要被调用，开netty http服务与dubbo服务端口偏移量1000
     *   启用数据压缩，可能遇到大对象参数等
     * 4.数据迁移完成后，注册dubbo服务
     * 5.定时n秒后或添加provider filter服务第一次被调用m秒后，依次调用所有迁移数据节点可清空用户数据（用户数据已移过来了失败不用管）
     *   可能依次调用清空数据过程中又会有数据更新需要订阅
     *
     * 关闭时检查默认有备份注册备份，无备份推送尝试数据到其他节点
     */
    @Deprecated
    private void nameServerTestCode() {
        String serverAddr = "124.222.131.236:3848";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);

        // 同步一致性 hash 临近节点数据
        try {
            // 获取当前服务实例
            NamingService namingService = NacosFactory.createNamingService(properties);
            List<Instance> imRouterInstances = namingService.getAllInstances(AppConstant.APP_IM_ROUTER_NAME);

            //namingService.registerInstance();
            Instance instance1 = new Instance();
            instance1.addMetadata("LOCK_VAL", "123123");
            // instance1.getMetadata();

            logger.info("instances: {}", imRouterInstances);
            if (!Collects.isEmpty(imRouterInstances)) {
                Map<String, String> consistentHashNodeMap = new HashMap<>();
                Map<String, Instance> stringInstanceMap = Collects.collect2Map(imRouterInstances,
                        Instance::toInetAddr,
                        new HashMap<>(Collects.mapCapacity(imRouterInstances.size()))
                );
                Map<String, String> map = new HashMap<>(Collects.mapCapacity(imRouterInstances.size()));
                for (Instance instance : imRouterInstances) {
                    String serverInetAddr = instance.toInetAddr();// 服务地址, 如: 192.168.56.1:3031
                    map.put(serverInetAddr, serverInetAddr);
                }
                UidConsistentHashSelector<String> selector = new UidConsistentHashSelector<>(map, imRouterInstances.hashCode());

                logger.info("instances addr: {}", imRouterInstances.get(0).toInetAddr());
                logger.info("instances addr: {}:{}", imRouterInstances.get(0).getIp(), imRouterInstances.get(0).getPort());
            }
            logger.info("application ready event...");
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

}
