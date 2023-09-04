package org.cache.evict;

import org.cache.api.IEvict;

public class EvictNull<K,V> implements IEvict<K,V> {

    @Override
    public void evict(K key) {

    }

    @Override
    public void remove(K key) {

    }
}
