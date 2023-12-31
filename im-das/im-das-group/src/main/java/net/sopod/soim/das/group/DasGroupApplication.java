package net.sopod.soim.das.group;

import net.sopod.soim.launcher.SoimApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
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
        SoimApplication.run(DasGroupApplication.class, args);
    }

}
