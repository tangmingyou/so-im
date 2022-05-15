package net.sopod.soim.logic.friend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FriendApplication
 *
 * @author tmy
 * @date 2022-05-15 22:22
 */
@EnableDubbo(scanBasePackages = {"net.sopod.soim.logic.friend.service"})
@SpringBootApplication
public class FriendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendApplication.class, args);
    }

}
