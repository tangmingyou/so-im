package net.sopod.soim.das.user;

import net.sopod.soim.launcher.SoimApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
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
        SoimApplication.run(DasUserApplication.class, args);
    }

}
