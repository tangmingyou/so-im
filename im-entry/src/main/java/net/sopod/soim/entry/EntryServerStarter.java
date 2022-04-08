package net.sopod.soim.entry;

import net.sopod.soim.entry.server.EntryServer;
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
public class EntryServerStarter implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(EntryServerStarter.class);

    @Override
    public void run(ApplicationArguments args) {
        EntryServer entryServer = new EntryServer("entry server", 8088);
        entryServer.startServer(err -> {
            logger.error("EntryServer 启动失败:", err);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(entryServer::shutdown));
    }

}
