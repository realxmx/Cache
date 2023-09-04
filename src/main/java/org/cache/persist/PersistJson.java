package org.cache.persist;

import com.alibaba.fastjson2.JSON;
import com.github.houbb.heaven.util.io.FileUtil;
import org.cache.api.IPersist;
import org.cache.core.Cache;
import org.cache.load.LoadJson;

import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistJson<K,V> implements IPersist {
    private final String path;
    private Long checkTime;
    private final Cache<K,V> cache;
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask(){
        public void run() {
            cachePersist();
        }
    };

    private final Logger logger = LoggerFactory.getLogger(LoadJson.class);

    public PersistJson(String path, Cache<K,V> cache, int timeNum, TimeUnit timeUnit){
        this.path = path;
        this.checkTime = timeUnit.toMillis(timeNum);
        this.cache = cache;
        timer.schedule(task, 1000, checkTime);
    }

    public void setCheckTime(int timeNum, TimeUnit timeUnit){
        this.checkTime = timeUnit.toMillis(timeNum);
        timer.cancel();
        timer.schedule(task, 1000, checkTime);
    }

    public void cachePersist(){
        Set<Map.Entry<K,V>> entries = cache.getAll();
        FileUtil.createFile(path);
        FileUtil.truncate(path);

        logger.debug("Persisting json");
        for(Map.Entry<K,V> entry:entries){
            String line = JSON.toJSONString(entry);
            FileUtil.write(path,line, StandardOpenOption.APPEND);
        }
        logger.debug("Persisting completed");
    }
}
