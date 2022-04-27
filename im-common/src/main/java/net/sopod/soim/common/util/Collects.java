package net.sopod.soim.common.util;

import java.util.*;
import java.util.function.Function;

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

	public static <T,K,V> Map<K, List<V>> group(Collection<T> collect,
                                                Function<T, K> groupBy,
                                                Function<T, V> valBy) {
		return group(3, collect, groupBy, valBy);
	}

    /**
     * 对 list 数据进行分组
     * @param aboutFactorOfSize 集合数据条数约是分组后数据的n倍
     * @param collect 集合数据
     * @param groupBy 集合元素分组值
     * @param valBy 分组结果
     * @param <T> 集合元素类型
     * @param <K> 分组值类型
     * @param <V> 分组结果类型
     * @return 分组结果
     */
	public static <T,K,V> Map<K, List<V>> group(int aboutFactorOfSize,
												Collection<T> collect,
												Function<T, K> groupBy,
												Function<T, V> valBy) {
		if (aboutFactorOfSize < 1) {
			throw new IllegalArgumentException("aboutFactorOfSize需大于0");
		}
		if (collect == null || collect.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<K, List<V>> result = new HashMap<>(Math.max(6, Math.min(16, collect.size() / aboutFactorOfSize)));
		for (T item : collect) {
			K key = groupBy.apply(item);
			V val = valBy.apply(item);
			List<V> vals = result.computeIfAbsent(key, k -> new LinkedList<>());
			vals.add(val);
		}
		return result;
	}

}
