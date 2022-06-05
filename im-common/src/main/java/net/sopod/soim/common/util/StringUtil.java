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

    public static boolean isEmpty(String content) {
        return content == null || content.length() == 0;
    }

    public static boolean isBlank(String str) {
        return !hasText(str);
    }

    public static boolean hasText(String str) {
        return str != null && !str.isEmpty() && containsText(str);
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();

        for(int i = 0; i < strLen; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }

    public static String toString(Object data) {
        return data == null ? null : data.toString();
    }

}
