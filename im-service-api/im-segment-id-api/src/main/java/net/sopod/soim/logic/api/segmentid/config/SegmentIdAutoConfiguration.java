package net.sopod.soim.logic.api.segmentid.config;

import net.sopod.soim.logic.api.segmentid.core.SegmentIdGenerator;
import net.sopod.soim.logic.api.segmentid.service.SegmentIdService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SegmentIdAutoConfiguration
 * o(￣ヘ￣o＃)
 *
 * @author tmy
 * @date 2022-04-05 21:02
 */
@ConditionalOnProperty(
        prefix = "im-segment-id",
        name = {"enabled"},
        matchIfMissing = true
)
@Configuration
public class SegmentIdAutoConfiguration {

    @DubboReference//(version = "*") //SegmentIdService.Version.v1)
    private SegmentIdService segmentIdService;

    @Bean
    public SegmentIdGenerator segmentIdGenerator() {
        if (segmentIdService == null) {
            throw new NullPointerException("segmentIdService bean not exists!");
        }
        return new SegmentIdGenerator(segmentIdService);
    }

}
