package org.cache.persist;

import org.cache.api.IPersist;

import java.util.concurrent.TimeUnit;

public class PersistNull<K,V> implements IPersist {
    @Override
    public void setCheckTime(int timeNum, TimeUnit timeUnit) {

    }

    @Override
    public void cachePersist() {

    }
}
