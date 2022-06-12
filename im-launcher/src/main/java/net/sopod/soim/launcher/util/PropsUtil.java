package net.sopod.soim.launcher.util;

import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * PropsUtil
 *
 * @author tmy
 * @date 2022-06-10 20:03
 */
public class PropsUtil {

    public static void setProperty(Properties props, String key, String value) {
        if (StringUtils.isEmpty(props.getProperty(key))) {
            props.setProperty(key, value);
        }
    }

}
