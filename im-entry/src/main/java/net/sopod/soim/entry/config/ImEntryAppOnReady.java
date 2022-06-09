package net.sopod.soim.entry.config;

import net.sopod.soim.common.constant.AppConstant;
import net.sopod.soim.entry.registry.ProtoMessageHandlerRegistry;
import net.sopod.soim.entry.server.EntryServer;
import net.sopod.soim.entry.worker.WorkerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * ImEntryAppOnReady
 *
 * @author tmy
 * @date 2022-06-09 10:45
 */
@Configuration
public class ImEntryAppOnReady implements ApplicationListener<ApplicationReadyEvent>, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ImEntryAppOnReady.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ImEntryAppContext.setContext(event.getApplicationContext());
        // 注册 protobuf 消息 handler
        ProtoMessageHandlerRegistry.registerHandlerWithApplicationContext(event.getApplicationContext());

        // 启动 entry-server
        EntryServerConfig config = ImEntryAppContext.getBean(EntryServerConfig.class);

        EntryServer entryServer = new EntryServer(
                config.getName(),
                // 使用dubbo 服务端口偏移量
                ImEntryAppContext.getAppPort() + AppConstant.IM_ENTRY_SERVER_OFFSET);
        entryServer.startServer(err -> {
            logger.error("EntryServer 启动失败:", err);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(entryServer::shutdown));

        // 启动消息消费队列组
        WorkerGroup.init(config.getWorkerSize());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
