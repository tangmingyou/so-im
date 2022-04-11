package net.sopod.soim.entry.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * EntryServerConfig
 *
 * @author tmy
 * @date 2022-04-11 16:08
 */
@Component
@ConfigurationProperties(prefix = "entry")
@EnableConfigurationProperties
@Data
public class EntryServerConfig {

    /** 消息消费者线程数 */
    private Integer workerSize;

    public Integer getWorkerSize() {
        if (workerSize == null || workerSize < 1) {
            workerSize = Runtime.getRuntime().availableProcessors();
            workerSize = Math.max(workerSize, 4);
        }
        return workerSize;
    }

}
