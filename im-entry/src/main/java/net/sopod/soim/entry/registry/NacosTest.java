package net.sopod.soim.entry.registry;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Properties;

/**
 * NacosTest
 *
 * @author tmy
 * @date 2022-04-12 12:42
 */
public class NacosTest {

    public static void main(String[] args) throws NacosException {
        String serverAddr = "124.222.131.236:3848";
        String dataId = "im-logic-user";
        String group = "DEFAULT_GROUP";
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);

        NamingService namingService = NacosFactory.createNamingService(properties);
        List<Instance> allInstances = namingService.getAllInstances("im-logic-user");
        System.out.println(allInstances);
        List<Instance> instances = namingService.selectInstances("consumer:", true);
        System.out.println(instances);

        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
    }

}
