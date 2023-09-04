package org.cache.core;

import org.cache.api.*;
import org.cache.evict.*;
import org.cache.expire.*;
import org.cache.load.*;
import org.cache.persist.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cache<K, V> implements ICache<K, V> {

    private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
    private IEvict<K, V> evict = new EvictNull<>();
    private IExpire<K,V> expire = new ExpireNull<>();
    private IPersist persist = new PersistNull();
    private ILoad load = new LoadNull();

    private final Logger logger = LoggerFactory.getLogger(Cache.class);

    public Cache<K,V> enableEvict(int max){
        evict = new EvictLRU<>(this,max);
        return this;
    }

    public Cache<K,V> enableExpire(int timeNum,TimeUnit timeUnit){
        expire = new Expire<>(this,timeNum,timeUnit);
        return this;
    }

    public Cache<K,V> enablePersist(String path,int timeNum,TimeUnit timeUnit){
        persist = new PersistJson<>(path,this,timeNum,timeUnit);
        return this;
    }

    public Cache<K,V> enableLoad(String path){
        load = new LoadJson<>(path,this);
        load.cacheLoad();
        return this;
    }


    @Override
    public boolean has(K key) {
        return map.containsKey(key);
    }

    @Override
    public void remove(K key) {
        evict.remove(key);
        map.remove(key);
        logger.debug("remove key:{}",key);
    }

    @Override
    public void put(K key, V value) {
        evict.evict(key);
        map.put(key, value);
        logger.debug("put key:{}",key);
    }

    public void put(K key, V value, int timeNum, TimeUnit timeUnit) {
        evict.evict(key);
        map.put(key, value);
        expire.setTime(key,System.currentTimeMillis()+timeUnit.toMillis(timeNum));
        logger.debug("put key:{} and set time",key);
    }

    public void setExpire(K key,int timeNum, TimeUnit timeUnit){
        expire.setTime(key,System.currentTimeMillis()+timeUnit.toMillis(timeNum));
        logger.debug("key:{} set time",key);
    }

    @Override
    public V get(K key) {
        if(expire.isExpire(key))
            remove(key);
        evict.evict(key);
        logger.debug("get key:{}",key);
        return map.get(key);
    }

    public List<V> getValues(K... keys){
        List<V> values = new ArrayList<>();
        for (K key:keys){
            values.add(get(key));
        }
        return values;
    }

    public Long getTime(K key){
        return expire.getTime(key);
    }

    public Set<K> getAllKeys(){
        return map.keySet();
    }

    public Collection<V> getAllValues(){
        return map.values();
    }

    public Set<Map.Entry<K,V>> getAll(){
        return map.entrySet();
    }

}
