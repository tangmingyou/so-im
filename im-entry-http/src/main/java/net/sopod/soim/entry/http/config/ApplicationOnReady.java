package net.sopod.soim.entry.http.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import net.sopod.soim.common.constant.AppConstant;
import org.apache.dubbo.common.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.Properties;

/**
 * ApplicationOnReady
 *
 * @author tmy
 * @date 2022-06-09 11:16
 */
@Configuration
public class ApplicationOnReady implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationOnReady.class);

    private final String serverAddress;

    public ApplicationOnReady(@Value("${dubbo.registry.address}") String serverAddress) {
        this.serverAddress = URL.valueOf(serverAddress).getAddress();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent readyEvent) {
        // NamingServer 获取 im-entry 实例，连接上，
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddress);

        NamingService namingService = null;
        try {
            namingService = NacosFactory.createNamingService(properties);
            List<Instance> allInstances = namingService.getAllInstances(AppConstant.APP_IM_ENTRY_NAME);
            logger.info("im-entry instance...: {}", allInstances);
            namingService.subscribe(AppConstant.APP_IM_ENTRY_NAME, (Event event) -> {
                NamingEvent ne = (NamingEvent) event;
                logger.info("namingEvent: {}", ne);
            });

        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
