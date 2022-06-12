package net.sopod.soim.logic.group;

import net.sopod.soim.launcher.SoimApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LogicGroupApplication
 *
 * @author tmy
 * @date 2022-06-03 21:04
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = "net.sopod.soim.logic.group.service")
public class LogicGroupApplication {

    public static void main(String[] args) {
        SoimApplication.run(LogicGroupApplication.class);
    }

}
