package org.cache.evict;

import org.cache.api.IEvict;
import org.cache.core.Cache;

import java.util.LinkedList;
import java.util.Queue;

public class EvictFIFO<K,V> implements IEvict<K,V> {
    private final Queue<K> queue = new LinkedList<>();
    private final Cache<K,V> cache;
    private final int max;

    public EvictFIFO(Cache<K, V> cache,int max) {
        this.cache = cache;
        this.max = max;
    }

    public void evict(K key) {
        if (queue.size() == max) {
            cache.remove(queue.remove());
        }
        queue.add(key);
    }

    public void remove(K key) {
        queue.remove(key);
    }
}
