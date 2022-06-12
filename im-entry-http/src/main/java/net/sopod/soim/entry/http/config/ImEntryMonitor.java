package net.sopod.soim.entry.http.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.dubbo.exception.SoimException;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.netty.FastThreadLocalThreadFactory;
import net.sopod.soim.data.msg.monitor.EntryMonitor;
import net.sopod.soim.entry.http.client.EntryClient;
import org.apache.dubbo.common.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * ApplicationOnReady
 *
 * @author tmy
 * @date 2022-06-09 11:16
 */
@Component
// @Configuration
public class ImEntryMonitor implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryMonitor.class);

    private final String nacosServerAddress;

    private NamingService namingService;

    private final Map<String, EntryClient> entryClientMap = new ConcurrentHashMap<>(8);

    private final ScheduledExecutorService scheduledExecutorService;

    public ImEntryMonitor(@Value("${dubbo.registry.address}") String nacosServerAddress) {
        this.nacosServerAddress = URL.valueOf(nacosServerAddress).getAddress();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1,
                new FastThreadLocalThreadFactory("entry-monitor-%d", Thread.NORM_PRIORITY));
        this.scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                for (Map.Entry<String, EntryClient> entry : entryClientMap.entrySet()) {
                    try {
                        EntryClient entryClient = entry.getValue();
                        // 定时获取状态信息
                        EntryMonitor.ReqEntryStatus req = EntryMonitor.ReqEntryStatus.newBuilder().build();
                        CompletableFuture<EntryMonitor.ResEntryStatus> future = entryClient.send(req);
                        EntryMonitor.ResEntryStatus res = future.join();
                        entryClient.updateEntryStatus(res.getConnections(), res.getStatusTime());
                    } catch (SoimException e) {
                        logger.error("获取状态信息失败", e);
                        entryClientMap.remove(entry.getKey(), entry.getValue());
                    }
                }
            } catch (Exception e) {
                logger.error("定时拉取状态信息错误", e);
            }
        }, 5, 5, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (namingService != null) {
                try {
                    namingService.shutDown();
                } catch (NacosException e) {
                    logger.error("nacos naming server shutdown exception", e);
                }
                scheduledExecutorService.shutdownNow();
                entryClientMap.values().forEach(EntryClient::close);
                logger.info("entry monitor shutdown.");
            }
        }));
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent readyEvent) {
        // NamingServer 获取 im-entry 实例，连接上，
        Properties properties = new Properties();
        properties.put("serverAddr", nacosServerAddress);
        try {
            this.namingService = NacosFactory.createNamingService(properties);
            // 初次启动获取所有 im-entry 服务节点
            List<Instance> imEntryInstances = this.namingService.getAllInstances(AppConstant.APP_IM_ENTRY_NAME);
            this.buildEntryMonitorMap(imEntryInstances);
            // 监听 im-entry 服务变化
            this.namingService.subscribe(AppConstant.APP_IM_ENTRY_NAME, (Event event) -> {
                if (!(event instanceof NamingEvent)) {
                    return;
                }
                NamingEvent ne = (NamingEvent) event;
                try {
                    this.buildEntryMonitorMap(ne.getInstances());
                }catch (Exception e) {
                    logger.error("构建im-entry服务节点失败:", e);
                }
            });

        } catch (NacosException e) {
            logger.error("nacos exception", e);
        }
    }

    /**
     * 重新构建 im-entry 服务节点记录
     */
    private synchronized void buildEntryMonitorMap(List<Instance> imEntryInstances) {
        HashSet<String> instanceAddresses = new HashSet<>(Collects.mapCapacity(imEntryInstances.size()));
        imEntryInstances.forEach(instance -> {
            String address = instance.getIp() + ":" + (instance.getPort() + AppConstant.IM_ENTRY_SERVER_OFFSET);
            instanceAddresses.add(address);
        });
        logger.info("im-entry instances: {}", instanceAddresses);
        // 关闭失效im-entry
        List<String> invalidateAddresses = new ArrayList<>();
        for (Map.Entry<String, EntryClient> entry : entryClientMap.entrySet()) {
            if (!instanceAddresses.contains(entry.getKey())) {
                invalidateAddresses.add(entry.getKey());
            }
        }
        for (String invalidateAddress : invalidateAddresses) {
            EntryClient invalidClient = entryClientMap.remove(invalidateAddress);
            invalidClient.close();
            logger.info("invalid im-entry {} closed.", invalidateAddress);
        }

        // 添加新的 im-entry 连接
        for (String instanceAddress : instanceAddresses) {
            if (entryClientMap.containsKey(instanceAddress)) {
               continue;
            }
            // nacos 注册时, entry server 还未启动完成，延时 5s 连接
            this.scheduledExecutorService.schedule(() -> {
                EntryClient entryClient = new EntryClient(instanceAddress);
                logger.info("连接: {}", instanceAddress);
                boolean connected = entryClient.connect();
                if (!connected) {
                    logger.error("im-entry {} 连接失败", instanceAddress);
                    return;
                }
                entryClientMap.put(instanceAddress, entryClient);
            }, 5, TimeUnit.SECONDS);
        }
    }

    /**
     * 获取一个活跃的 im-entry 服务器地址
     */
    public String getOneActiveImEntryAddress() {
        if (entryClientMap.size() == 0) {
            return null;
        }
        Collection<EntryClient> clients = entryClientMap.values();
        List<EntryClient> sortedClients = clients.stream()
                .sorted(Comparator.comparing(EntryClient::getEntryConnections))
                .collect(Collectors.toList());
        logger.info("entrys: {}", sortedClients);
        return sortedClients.get(0).getEntryAddress();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
