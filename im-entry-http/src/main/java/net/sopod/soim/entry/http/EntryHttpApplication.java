package net.sopod.soim.entry.http;

import net.sopod.soim.launcher.SoimApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EntryHttpApplication
 *
 * @author tmy
 * @date 2022-04-13 23:08
 */
@EnableDubbo
@SpringBootApplication
public class EntryHttpApplication {

    public static void main(String[] args) {
        SoimApplication.run(EntryHttpApplication.class, args);
    }

}
