package net.sopod.soim.das.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DasMessageApplication
 *
 * @author tmy
 * @date 2022-06-03 11:53
 */
@MapperScan("net.sopod.soim.das.message.dao")
@SpringBootApplication
public class DasMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DasMessageApplication.class);
    }

}
