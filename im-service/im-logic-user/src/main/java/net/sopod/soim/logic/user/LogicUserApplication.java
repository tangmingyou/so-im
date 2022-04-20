package net.sopod.soim.logic.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * UserMain
 *
 * @author tmy
 * @date 2022-03-26 01:41
 */
@EnableDubbo(scanBasePackages = {"net.sopod.soim.logic.user.service"})
@EnableConfigurationProperties
@SpringBootApplication
public class LogicUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogicUserApplication.class, args);
    }

}

