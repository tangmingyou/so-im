package net.sopod.soim.logic.segmentid;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SegmentIdApplication
 *
 * @author tmy
 * @date 2022-04-02 14:55
 */
@EnableDubbo(scanBasePackages = {"net.sopod.soim.logic.segmentid.service"})
@MapperScan("net.sopod.soim.logic.segmentid.mapper")
@SpringBootApplication
public class SegmentIdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SegmentIdApplication.class);
    }

}