package net.sopod.soim.entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EntryMain
 *
 * @author tmy
 * @date 2022-03-26 00:06
 */
@SpringBootApplication
public class EntryApplication {

    private static final Logger logger = LoggerFactory.getLogger(EntryApplication.class);

    public static void main(String[] args) {
        logger.info("二狗腿子:{}", 1);
        logger.info("二狗腿子:{}", 2);
        logger.info("二狗腿子:{}", 3);
        System.out.println("狗腿子4");
        SpringApplication.run(EntryApplication.class, args);
    }

}
