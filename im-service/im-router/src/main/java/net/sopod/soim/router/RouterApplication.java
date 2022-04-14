package net.sopod.soim.router;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RouterApplication
 *
 * @author tmy
 * @date 2022-04-14 11:45
 */
@EnableDubbo(scanBasePackages = {"net.sopod.soim.router.service"})
@SpringBootApplication
public class RouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RouterApplication.class, args);
    }

}
