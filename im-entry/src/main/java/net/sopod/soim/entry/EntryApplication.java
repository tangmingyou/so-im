package net.sopod.soim.entry;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EntryMain
 *
 * @author tmy
 * @date 2022-03-26 00:06
 */
@SpringBootApplication
@EnableDubbo(scanBasePackages = {"net.sopod.soim.entry.service"})
public class EntryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntryApplication.class, args);
    }

}
