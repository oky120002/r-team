package com.r.web.vote931.support.bean;

import java.util.Map;

/**
 * 键值对
 * 
 * @author oky
 * @version 版本号, 2013-10-14
 */
public class KeyValue<Key, Value> implements Map.Entry<Key, Value> {
    public Key key;
    public Value value;

    /** 创建"键值对" */
    public static <K, V> KeyValue<K, V> kv(K key, V value) {
        KeyValue<K, V> kv = new KeyValue<K, V>();
        kv.key = key;
        kv.value = value;
        return kv;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public Value setValue(Value value) {
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
