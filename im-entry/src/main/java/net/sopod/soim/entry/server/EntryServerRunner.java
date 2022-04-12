package net.sopod.soim.entry.server;

import net.sopod.soim.entry.config.EntryServerConfig;
import net.sopod.soim.entry.worker.WorkerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ServerRunner
 *
 * @author tmy
 * @date 2022-03-29 00:22
 */
@Component
public class EntryServerRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(EntryServerRunner.class);

    private final EntryServerConfig config;

    public EntryServerRunner(EntryServerConfig config) {
        this.config = config;
    }

    @Override
    public void run(ApplicationArguments args) {
        EntryServer entryServer = new EntryServer(this.config.getName(), this.config.getPort());
        entryServer.startServer(err -> {
            logger.error("EntryServer 启动失败:", err);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(entryServer::shutdown));

        // 启动消息消费队列组
        WorkerGroup.init(config.getWorkerSize());
    }

}
