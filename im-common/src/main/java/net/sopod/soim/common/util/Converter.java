package net.sopod.soim.common.util;

/**
 * Converter
 *
 * @author tmy
 * @date 2022-05-30 22:23
 */
public class Converter {

    public static String toString(Object data) {
        return data == null ? null : data.toString();
    }

    /**
     * @exception  NumberFormatException  if the string cannot be parsed as an integer.
     */
    public static int toInt(Object value) {
        if (value instanceof Number) {
            return ((Number)value).intValue();
        }
        return Integer.parseInt(String.valueOf(value));
    }

}
