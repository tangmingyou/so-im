package net.sopod.soim.router.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.common.constant.DubboConstant;
import net.sopod.soim.common.util.Collects;
import net.sopod.soim.common.util.HashAlgorithms;
import net.sopod.soim.common.util.StringUtil;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.support.RegistryManager;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ImRouterContextHolder
 * im-router 应用上下文信息
 * TODO 启动指定 im-router 服务类型 service/backup
 * TODO backup 指定备份 im-router 或随机备份未备份 im-router 服务
 *
 * @author tmy
 * @date 2022-05-04 09:21
 */
public class ImRouterAppContext {

    private static final Logger logger = LoggerFactory.getLogger(ImRouterAppContext.class);

    private static ApplicationContext applicationContext;

    /**
     * 要注册的 provider 服务的 url 列表
     */
    private static final List<URL> registryInvokerUrls = new CopyOnWriteArrayList<>();

    private static String discoveryAddr;
    private static String appAddr;
    private static String appHost;
    private static int appPort;

    public static final String IM_ROUTER_ID;

    static {
        IM_ROUTER_ID = String.valueOf(HashAlgorithms.md5Hash(StringUtil.randomUUID()));
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ImRouterAppContext.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> type) {
        return applicationContext.getBean(type);
    }

    public static void addRegistryInvokerUrl(URL registryInvokerUrl) {
        registryInvokerUrls.add(registryInvokerUrl);
    }

    public static List<URL> getRegistryInvokerUrls() {
        return registryInvokerUrls;
    }

    public static void setAppServiceAddr(String host, int port) {
        ImRouterAppContext.appAddr = host + ":" + port;
        ImRouterAppContext.appHost = host;
        ImRouterAppContext.appPort = port;
    }

    public static void setServiceDiscoveryRegistryAddr(String discoveryAddr) {
        ImRouterAppContext.discoveryAddr = discoveryAddr;
    }

    public static String getDiscoveryAddr() {
        return discoveryAddr;
    }

    public static String getAppAddr() {
        return appAddr;
    }

    public static String getAppHost() {
        return appHost;
    }

    public static int getAppPort() {
        return appPort;
    }

    private static NamingService namingService;

    /**
     * 获取 im-router 集群节点信息
     * TODO 注册应用关闭
     */
    public static List<Instance> getClusterImRouterInstance() {
        String discoveryAddr;
        if (null == (discoveryAddr = ImRouterAppContext.getDiscoveryAddr())) {
            return Collections.emptyList();
        }
        if (namingService == null) {
            synchronized (ImRouterAppContext.class) {
                if (namingService == null) {
                    Properties properties = new Properties();
                    properties.put("serverAddr", discoveryAddr);
                    // 获取当前服务实例
                    try {
                        namingService = NacosFactory.createNamingService(properties);
                    } catch (NacosException e) {
                        throw new IllegalStateException("nacos实例"+discoveryAddr+"连接失败", e);
                    }
                }
            }
        }
        try {
            return namingService.getAllInstances(AppConstant.APP_IM_ROUTER_NAME);
        } catch (NacosException e) {
            throw new IllegalStateException(AppConstant.APP_IM_ROUTER_NAME + "集群信息获取失败:", e);
        } finally {
            if (namingService != null) {
                try {
                    namingService.shutDown();
                } catch (NacosException e) {
                    logger.error("查询{}服务NamingServer关闭失败!", AppConstant.APP_IM_ROUTER_NAME, e);
                }
            }
        }
    }

    /**
     * 注册 im-router 的API接口服务
     */
    public static void doRegistry() {
        RegistryManager registryManager = ApplicationModel.defaultModel().getBeanFactory()
                .getBean(RegistryManager.class);
        Collection<Registry> registries = registryManager.getRegistries();
        List<URL> registryInvokerUrls = ImRouterAppContext.getRegistryInvokerUrls();
        if (Collects.isNotEmpty(registries)
                && Collects.isNotEmpty(registryInvokerUrls)) {
            for (Registry registry : registries) {
                for (URL invokerUrl : registryInvokerUrls) {
                    // 添加 im-router 服务id参数，生成新的 url
                    URL url = invokerUrl.addParameter(
                            DubboConstant.IM_ROUTER_ID_KEY,
                            ImRouterAppContext.IM_ROUTER_ID
                    );
                    registry.register(url);
                }
            }
        }
    }

}
