package net.sopod.soim.entry;

import com.alibaba.nacos.api.exception.NacosException;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EntryMain
 *
 * @author tmy
 * @date 2022-03-26 00:06
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = {"net.sopod.soim.entry.service"})
public class EntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntryApplication.class, args);
    }

}
