package net.sopod.soim.router.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.router.api.route.UidConsistentHashSelector;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.support.RegistryManager;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.proxy.AbstractProxyInvoker;
import org.apache.dubbo.spring.boot.context.event.AwaitingNonWebApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.*;

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
        // 服务已可用进行注册
        this.doRegistry();
    }

    /**
     * 注册 im-router 的API接口服务
     */
    private void doRegistry() {
        RegistryManager registryManager = ApplicationModel.defaultModel().getBeanFactory()
                .getBean(RegistryManager.class);
        Collection<Registry> registries = registryManager.getRegistries();
        List<URL> registryInvokerUrls = ImRouterAppContextHolder.getRegistryInvokerUrls();
        if (Collects.isNotEmpty(registries)
                && Collects.isNotEmpty(registryInvokerUrls)) {
            for (Registry registry : registries) {
                for (URL invokerUrl : registryInvokerUrls) {
                    // 添加 im-router 服务id参数，生成新的 url
                    URL url = invokerUrl.addParameter(
                            DubboConstant.IM_ROUTER_ID_KEY,
                            ImRouterAppContextHolder.IM_ROUTER_ID
                    );
                    registry.register(url);
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
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
     * 4.数据迁移完成后，注册dubbo服务
     * 5.定时n秒后或添加provider filter服务第一次被调用m秒后，依次调用所有迁移数据节点可清空用户数据（用户数据已移过来了失败不用管）
     *   可能依次调用清空数据过程中又会有数据更新需要订阅
     */
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
