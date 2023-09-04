package org.cache.api;

public interface IEvict<K,V> {

    public void evict(K key);

    public void remove(K key);
}
