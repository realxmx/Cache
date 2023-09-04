package org.cache.expire;


import org.cache.api.IExpire;

import java.util.concurrent.TimeUnit;

public class ExpireNull<K,V> implements IExpire<K,V> {

    @Override
    public boolean isExpire(K key) {
        return false;
    }

    @Override
    public Long getTime(K key) {
        return null;
    }

    @Override
    public void setTime(K key, Long time) {

    }

    @Override
    public void setCheckTime(int timeNum, TimeUnit timeUnit) {

    }
}
