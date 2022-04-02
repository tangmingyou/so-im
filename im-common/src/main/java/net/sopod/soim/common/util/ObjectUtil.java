package net.sopod.soim.common.util;

import com.google.common.base.Preconditions;

/**
 * ObjectUtil
 *
 * @author tmy
 * @date 2022-04-02 16:54
 */
public class ObjectUtil {

    public static <T> T defaultValue(T value, T...defaultValues) {
        if (value != null) {
            return value;
        }
        Preconditions.checkArgument(defaultValues != null && defaultValues.length > 0, "默认值不能为空");
        T result = null;
        for (T defaultValue : defaultValues) {
            if (defaultValue != null) {
                result = defaultValue;
                break;
            }
        }
        Preconditions.checkNotNull(result, "默认值至少一个不为空");
        return result;
    }

}
