package com.r.boda.uploadservice.core;

import java.util.Map;

/**
 * 键值对
 * 
 * @author oky
 * @version 版本号, 2013-10-14
 */
public class KV<K, V> implements Map.Entry<K, V> {
	public K key;
	public V value;

	/** 创建"键值对" */
	public static <K, V> KV<K, V> kv(K key, V value) {
		KV<K, V> kv = new KV<K, V>();
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
		if (!(o instanceof KV))
			return false;
		KV e = (KV) o;
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
