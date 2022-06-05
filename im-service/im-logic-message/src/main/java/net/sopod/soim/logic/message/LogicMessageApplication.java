package net.sopod.soim.logic.message;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LogicMessageApplication
 *
 * @author tmy
 * @date 2022-06-05 11:12
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "net.sopod.soim.logic.message.service")
public class LogicMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogicMessageApplication.class);
    }

}
