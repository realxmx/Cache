package org.cache.expire;

import org.cache.api.IExpire;
import org.cache.core.Cache;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.cache.load.LoadJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Expire<K, V> implements IExpire<K, V> {
    private final Map<K, Long> map = new ConcurrentHashMap<>();
    private Long checkTime;
    private final Cache<K, V> cache;
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            logger.debug("Timely expire");
            for (K key : map.keySet()) {
                if (map.get(key) < System.currentTimeMillis()) {
                    map.remove(key);
                    cache.remove(key);
                }
            }
            logger.debug("Timely expire completed");
        }
    };

    private final Logger logger = LoggerFactory.getLogger(LoadJson.class);
    public Expire(Cache<K, V> cache, int expireTime, TimeUnit timeUnit) {
        this.checkTime = timeUnit.toMillis(expireTime);
        this.cache = cache;
    }

    public void setCheckTime(int timeNum, TimeUnit timeUnit) {
        this.checkTime = timeUnit.toMillis(timeNum);
        timer.cancel();
        timer.schedule(task, 1000, checkTime);
    }

    public void setTime(K key, Long time) {
        map.put(key, time);
    }

    public Long getTime(K key) {
        return map.get(key);
    }

    public boolean isExpire(K key) {
        if (map.containsKey(key))
            if (map.get(key) < System.currentTimeMillis()) {
                map.remove(key);
                logger.debug("Lazy expire");
                return true;
            }
        return false;
    }

}
