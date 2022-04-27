package net.sopod.soim.entry.registry;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.entry.config.EntryServerConfig;
import org.apache.dubbo.registry.client.ServiceDiscoveryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * RegistryService
 *
 * @author tmy
 * @date 2022-04-27 16:38
 */
@Service
public class RegistryService {

    private static final Logger logger = LoggerFactory.getLogger(RegistryService.class);

    @Resource
    private EntryServerConfig entryServerConfig;

    //@Resource
    private ServiceDiscoveryRegistry serviceDiscoveryRegistry;

    private NamingService namingService;

    public void registryImEntry() throws NacosException {
        Properties properties = new Properties();
        properties.put("serverAddr", entryServerConfig.getNacosAddr());
        this.namingService = NacosFactory.createNamingService(properties);

        // 注册当前服务
        this.namingService.registerInstance(AppConstant.APP_IM_ENTRY_REGISTRY_NAME,
                entryServerConfig.getIp(),
                entryServerConfig.getPort()
        );
        logger.info("{} registry at {}:{}", AppConstant.APP_IM_ENTRY_REGISTRY_NAME, entryServerConfig.getIp(), entryServerConfig.getPort());
    }

}
