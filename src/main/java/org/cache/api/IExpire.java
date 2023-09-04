package org.cache.api;

import java.util.concurrent.TimeUnit;

public interface IExpire<K,V> {
    public boolean isExpire(K key);
    public Long getTime(K key);
    public void setTime(K key, Long time);
    public void setCheckTime(int timeNum, TimeUnit timeUnit);
}
