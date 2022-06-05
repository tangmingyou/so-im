package net.sopod.soim.das.group;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DasGroupApplication
 *
 * @author tmy
 * @date 2022-06-03 11:33
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "net.sopod.soim.das.group.service")
@MapperScan("net.sopod.soim.das.group.dao")
public class DasGroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasGroupApplication.class);
    }

}
