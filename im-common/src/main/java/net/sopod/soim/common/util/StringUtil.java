package net.sopod.soim.common.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * StringUtil
 *
 * @author tmy
 * @date 2022-04-14 11:10
 */
public class StringUtil {

    public static String randomUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return (new UUID(random.nextLong(), random.nextLong())).toString().replace("-", "");
    }

}
