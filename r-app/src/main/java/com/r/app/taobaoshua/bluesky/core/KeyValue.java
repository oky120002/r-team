/**
 * 描          述:  描述
 * 修  改   人:  oky
 * 修改时间:  2013-10-14
 * 修改描述:
 */
package com.r.app.taobaoshua.bluesky.core;

import java.util.Map;

/**
 * 键值对
 * 
 * @author oky
 * @version 版本号, 2013-10-14
 */
public class KeyValue<K, V> implements Map.Entry<K, V> {
	public K key;
	public V value;

	/** 创建"键值对" */
	public static <K, V> KeyValue<K, V> kv(K key, V value) {
		KeyValue<K, V> kv = new KeyValue<K, V>();
		kv.key = key;
		kv.value = value;
		return kv;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		return this.value = value;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public final boolean equals(Object o) {
		if (!(o instanceof KeyValue))
			return false;
		KeyValue e = (KeyValue) o;
		Object k1 = getKey();
		Object k2 = e.getKey();
		if (k1 == k2 || (k1 != null && k1.equals(k2))) {
			Object v1 = getValue();
			Object v2 = e.getValue();
			if (v1 == v2 || (v1 != null && v1.equals(v2)))
				return true;
		}
		return false;
	}

	@Override
	public final int hashCode() {
		return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
	}

	@Override
	public final String toString() {
		return getKey() + "=" + getValue();
	}
}
