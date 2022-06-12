package net.sopod.soim.logic.segmentid;

import net.sopod.soim.launcher.SoimApplication;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
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
        SoimApplication.run(SegmentIdApplication.class);
    }

}
