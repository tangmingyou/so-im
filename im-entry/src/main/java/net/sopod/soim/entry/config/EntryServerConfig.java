package net.sopod.soim.entry.config;

import lombok.Data;
import net.sopod.soim.common.constant.AppConstant;
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
@ConfigurationProperties(prefix = "entry-server")
@EnableConfigurationProperties
@Data
public class EntryServerConfig {

    private String name = AppConstant.APP_IM_ENTRY_NAME;

    /** entry 所在服务器 ip */
    private String ip = "127.0.0.1";

    private Integer port = 8088;

    private String nacosAddr;

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
