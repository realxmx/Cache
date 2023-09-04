package org.cache.evict;

import org.cache.api.IEvict;
import org.cache.core.Cache;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class EvictLRU<K, V> implements IEvict<K,V> {
    private final Queue<K> queue = new LinkedList<>();
    private final Cache<K,V> cache;
    private final int max;

    private final Logger logger = LoggerFactory.getLogger(EvictLRU.class);
    public EvictLRU(Cache<K, V> cache,int max) {
        this.cache = cache;
        this.max = max;
    }

    public void evict(K key) {
        if (cache.has(key)){
            queue.remove(key);
            logger.debug("evict same key:{}",key);
        }
        if (queue.size() == max) {
            cache.remove(queue.remove());
            logger.debug("evict eldest key");
        }
        queue.add(key);
    }

    public void remove(K key) {
        queue.remove(key);
    }
}
