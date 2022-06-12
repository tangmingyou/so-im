package net.sopod.soim.router;

import net.sopod.soim.launcher.SoimApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
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
        SoimApplication.run(RouterApplication.class, args);
    }

}
