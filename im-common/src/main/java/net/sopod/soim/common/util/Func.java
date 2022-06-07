package net.sopod.soim.common.util;

/**
 * Func
 *
 * @author tmy
 * @date 2022-05-01 15:28
 */
public class Func {

    public static <T> T nullSo(T data, T so) {
        return data == null ? data : so;
    }

}
