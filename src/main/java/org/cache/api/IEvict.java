package org.cache.api;

public interface IEvict<K,V> {

    void evict(K key);

    void remove(K key);
}
