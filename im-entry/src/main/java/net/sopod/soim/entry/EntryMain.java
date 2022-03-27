package net.sopod.soim.entry;

import net.sopod.soim.entry.model.A;
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
        logger.info("info");
        logger.warn("warn");
        logger.error("error...");

        A a = new A();
        a.setName("太极拳");
        System.out.println(a);
    }

}
