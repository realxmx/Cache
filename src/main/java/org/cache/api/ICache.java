package org.cache.api;

import org.cache.core.Cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface ICache<K,V> {

    Cache<K,V> enableEvict(int max);

    Cache<K,V> enableExpire(int timeNum,TimeUnit timeUnit);

    Cache<K,V> enablePersist(String path,int timeNum,TimeUnit timeUnit);

    Cache<K,V> enableLoad(String path);

    boolean has(K key);

    void remove(K key);

    void put(K key,V value);

    V get(K key);

    void put(K key, V value, int timeNum, TimeUnit timeUnit);

    void setExpire(K key,int timeNum, TimeUnit timeUnit);

    List<V> getValues(K... keys);

    Long getTime(K key);

    Set<K> getAllKeys();

    Collection<V> getAllValues();

    Set<Map.Entry<K,V>> getAll();
}
