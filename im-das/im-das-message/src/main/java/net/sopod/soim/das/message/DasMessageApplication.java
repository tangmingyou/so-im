package net.sopod.soim.das.message;

import net.sopod.soim.launcher.SoimApplication;
import org.mybatis.spring.annotation.MapperScan;
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
        SoimApplication.run(DasMessageApplication.class, args);
    }

}
