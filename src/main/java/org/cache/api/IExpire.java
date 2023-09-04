package org.cache.api;

import java.util.concurrent.TimeUnit;

public interface IExpire<K, V> {
    boolean isExpire(K key);

    Long getTime(K key);

    void setTime(K key, Long time);

    void setCheckTime(int timeNum, TimeUnit timeUnit);
}
