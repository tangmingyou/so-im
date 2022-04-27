package net.sopod.soim.entry;

import com.alibaba.nacos.api.exception.NacosException;
import net.sopod.soim.entry.registry.RegistryService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * EntryMain
 *
 * @author tmy
 * @date 2022-03-26 00:06
 */
@SpringBootApplication
@EnableDubbo
public class EntryApplication {

    public static void main(String[] args) throws NacosException {
        ConfigurableApplicationContext context = SpringApplication.run(EntryApplication.class, args);

        // 注册 im-entry 服务
        RegistryService registryService = context.getBean(RegistryService.class);
        registryService.registryImEntry();
    }

}
