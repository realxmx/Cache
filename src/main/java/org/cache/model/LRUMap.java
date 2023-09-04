package org.cache.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<K,V> extends LinkedHashMap<K,V> {
    private final int max;

    public LRUMap(int max) {
        super((int)(max*1.4),0.75f,true);
        this.max = max;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>max;
    }
}
