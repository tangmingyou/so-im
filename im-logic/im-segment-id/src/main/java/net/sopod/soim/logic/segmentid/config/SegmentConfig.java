package net.sopod.soim.logic.segmentid.config;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * SegmentConfigration
 *
 * @author tmy
 * @date 2022-04-02 17:02
 */
@EnableConfigurationProperties
@Configuration()
@Component
@Data
public class SegmentConfig {

    public static final String KEY_PREFIX_SEGMENT_ID_INSERT = "SEGMENT_ID_INSERT_";

    private long initId = 10000L;

    private long initStep = 1000L;

    private int dbSegmentVersionRetryTimes = 3;

}
