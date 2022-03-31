package net.sopod.soim.das.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DasUserApplication
 *
 * @author tmy
 * @date 2022-03-31 22:31
 */
@EnableDubbo(scanBasePackages = {"net.sopod.soim.das.user.service"})
@SpringBootApplication
public class DasUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasUserApplication.class, args);
    }

}
