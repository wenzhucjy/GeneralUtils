package com.github.mysite.common.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 名称: CollectionsConverterHelper.java<br>
 * 描述: List convert to Map helper etc.<br>
 * 类型: JAVA<br>
 * @since  2015年7月14日
 * @author jy.chen
 */
public class CollectionsConverterHelper {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CollectionsConverterHelper.class);

	/**
	 * ListAsMap
	 * 
	 * @param sourceList
	 * @param converter
	 * @return
	 */
	public static <K, V> Map<K, V> listAsMap(Collection<V> sourceList, ListToMapConverter<K, V> converter) {
		Map<K, V> newMap = new HashMap<K, V>();
		for (V item : sourceList) {
			newMap.put(converter.getKey(item), item);
		}
		return newMap;
	}

	public static interface ListToMapConverter<K, V> {
		public K getKey(V item);
	}

	/**
	 * sort Map by value
	 * 
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				int res = e1.getValue().compareTo(e2.getValue());
				if (e1.getKey().equals(e2.getKey())) {
					return res; // Code will now handle equality properly
				} else {
					return res != 0 ? res : 1; // While still adding all entries
				}
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	/**
	 * sort Map by key desc
	 * 
	 * @param unsortMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map sortByComparator(Map unsortMap) {

		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getKey()).compareTo(((Map.Entry) (o2)).getKey());
			}
		});

		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	/**
	 * Converts a <code>List</code> to a map. One of the methods of the list is
	 * called to retrive the value of the key to be used and the object itself
	 * from the list entry is used as the objct. An empty <code>Map</code> is
	 * returned upon null input. Reflection is used to retrieve the key from the
	 * object instance and method name passed in.
	 * 
	 * @param <K>
	 *            The type of the key to be used in the map
	 * @param <V>
	 *            The type of value to be used in the map and the type of the
	 *            elements in the collection
	 * @param coll
	 *            要转换的集合
	 * @param keyType
	 *            变量类型关键字的类
	 * @param valueType
	 *            变量类型值的类
	 * @param keyMethodName
	 *            方法名调用来检索每个实例的集合的关键
	 * @return Map<K,V> 实例
	 * @throws IllegalArgumentException
	 *             如果任何其他的参数是无效.
	 */
	public static <K, V> Map<K, V> asMap(final java.util.Collection<V> coll, final Class<K> keyType, final Class<V> valueType,
			final String keyMethodName) {

		final HashMap<K, V> map = new HashMap<K, V>();
		Method method = null;

		if (StringUtils.isEmpty(coll))
			return map;
		Assert.notNull(keyType);
		Assert.notNull(valueType);
		Assert.hasText(keyMethodName);

		try {
			// return the Method to invoke to get the key for the map
			method = valueType.getMethod(keyMethodName);
		} catch (final NoSuchMethodException e) {
			final String message = String.format("METHOD_NOT_FOUND,keyMethodName:[%s],valueType:[%s]", keyMethodName, valueType);
			LOG.error(message, e);
			throw new IllegalArgumentException(message, e);
		}
		try {
			for (final V value : coll) {

				Object object;
				object = method.invoke(value);
				@SuppressWarnings("unchecked")
				final K key = (K) object;
				map.put(key, value);
			}
		} catch (final Exception e) {
			final String message = String.format("METHOD_CALL_FAILED,method:[%s],valueType:[%s]", method, valueType);
			LOG.error(message, e);
			throw new IllegalArgumentException(message, e);
		}
		return map;
	}

	/**
	 * 比较集合对象的属性值是否一致
	 * 
	 * @param coll
	 * @param comp
	 * @return
	 */
	public static <T extends Object> boolean isEqual(Collection<? extends T> coll, Comparator<? super T> comp) {
		
		Iterator<? extends T> i = coll.iterator();
		T first = i.next();
		while (i.hasNext()) {
			T next = i.next();
			if (comp.compare(first, next) < 0) {
				return false;
			}
		}
		return true;
	}

	public static <T> List<T> search(final String str,Collection<? extends T> coll, Comparable<? super T> comp) {
		if (comp == null)
			return null;
		List<T> list = new ArrayList<T>();
		Iterator<? extends T> i = coll.iterator();
		while (i.hasNext()) {
			T next = i.next();
			if (comp.compareTo(next) > 0) {
				list.add(next);
			}
		}
		return list;
	}
}
