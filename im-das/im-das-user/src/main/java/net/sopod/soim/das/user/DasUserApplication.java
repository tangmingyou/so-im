package net.sopod.soim.das.user;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DasUserApplication
 *
 * @author tmy
 * @date 2022-03-31 22:31
 */
@SpringBootApplication
@MapperScan("net.sopod.soim.das.user.dao")
@EnableDubbo(scanBasePackages = {"net.sopod.soim.das.user.service"})
public class DasUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasUserApplication.class, args);
    }

}
