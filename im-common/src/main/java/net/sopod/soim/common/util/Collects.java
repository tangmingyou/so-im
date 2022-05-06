package net.sopod.soim.common.util;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

/**
 * Collects
 *
 * @author tmy
 * @date 2022-04-22 10:17
 */
public class Collects {

	public static boolean isEmpty(@Nullable Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(@Nullable Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean isEmpty(@Nullable Object[] arr) {
		return arr == null || arr.length == 0;
	}

	public static boolean isNotEmpty(@Nullable Object[] arr) {
		return !isEmpty(arr);
	}

	public static <T, K> Map<K, T> collect2Map(Collection<T> collect,
											   Function<T, K> keyProvider) {
		return collect2Map(collect, keyProvider, new HashMap<>());
	}

	public static <T, K> Map<K, T> collect2Map(Collection<T> collect,
													Function<T, K> keyProvider,
													Map<K, T> resultMap) {
		for (T item : collect) {
			resultMap.put(keyProvider.apply(item), item);
		}
		return resultMap;
	}

	public static <T, K, V> Map<K, V> collect2KvMap(Collection<T> collect,
													Function<T, K> keyProvider,
													Function<T, V> valueProvider) {
		return collect2KvMap(collect, keyProvider, valueProvider, new HashMap<>());
	}

	public static <T, K, V> Map<K, V> collect2KvMap(Collection<T> collect,
												  Function<T, K> keyProvider,
												  Function<T, V> valueProvider,
												  Map<K, V> resultMap) {
		for (T item : collect) {
			resultMap.put(keyProvider.apply(item), valueProvider.apply(item));
		}
		return resultMap;
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

	/**
	 * 根据元素个数计算 map 容量大小
	 * @param size 元素个数
	 * @return map 容量大小
	 */
	public static int mapCapacity(int size) {
		return Math.max(2, (int)Math.ceil(size / 0.75));
	}

}
