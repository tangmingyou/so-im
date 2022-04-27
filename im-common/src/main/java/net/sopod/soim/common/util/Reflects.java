package net.sopod.soim.common.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Reflects
 *
 * @author tmy
 * @date 2022-04-10 23:54
 */
public class Reflects {

    /**
     * 获取父类上的泛型
     * @return 父类上的泛型
     */
    public static List<String> getSuperClassGenericTypes(Class<?> clazz) {
        // 获取 handler 的泛型消息
        Type superType = clazz.getGenericSuperclass();
        String typeName = superType.getTypeName();
        return parseGenericType(typeName);
    }

    /**
     * 获取父接口上的泛型
     * @return 父接口上的泛型
     */
    public static List<String> getSuperInterfaceGenericTypes(Class<?> clazz) {
        // 获取 handler 的泛型消息
        Type[] genericTypes = clazz.getGenericInterfaces();
        List<String> types = new ArrayList<>(6);
        for (Type genericType : genericTypes) {
            List<String> interfaceGenericTypes = parseGenericType(genericType.getTypeName());
            types.addAll(interfaceGenericTypes);
        }
        return types;
    }

    private static List<String> parseGenericType(String genericClass) {
        int idx = genericClass.indexOf('<');
        if (idx == -1) {
            // 父类没有泛型
            return Collections.emptyList();
        }
        String genericName = genericClass.substring(idx + 1, genericClass.length() - 1);
        // 父类只有一个泛型
        if (!genericName.contains(",")) {
            return Collections.singletonList(genericName);
        }
        // 父类有多个泛型
        String[] genericNames = genericName.split(", ");
        return Arrays.asList(genericNames);
    }

}
