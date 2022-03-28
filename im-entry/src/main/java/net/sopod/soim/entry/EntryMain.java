package net.sopod.soim.entry;

import net.sopod.soim.entry.server.EntryServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EntryMain
 *
 * @author tmy
 * @date 2022-03-26 00:06
 */
public class EntryMain {

    private static final Logger logger = LoggerFactory.getLogger(EntryMain.class);

    public static void main(String[] args) {
        EntryServer entryServer = new EntryServer("entry server", 8088);
        entryServer.startServer(err -> {
            logger.error("EntryServer 启动失败:", err);
        });

        Runtime.getRuntime().addShutdownHook(new Thread(entryServer::shutdown));
    }

}
