package net.sopod.soim.common.util;

/**
 * Collects
 *
 * @author tmy
 * @date 2022-04-22 10:17
 */
public class Collects {

    /**
     * 数组翻转
     */
    public static long[] revers(long[] arr) {
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            long temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

}
