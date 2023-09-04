package org.cache.load;

import com.alibaba.fastjson2.JSON;
import com.github.houbb.heaven.util.io.FileUtil;
import org.cache.api.ILoad;
import org.cache.core.Cache;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadJson<K,V> implements ILoad {
    private final String path;
    private final Cache<K,V> cache;

    private final Logger logger = LoggerFactory.getLogger(LoadJson.class);
    public LoadJson(String path, Cache<K,V> cache){
        this.path = path;
        this.cache = cache;
    }

    public void cacheLoad(){
        if(!FileUtil.exists(path)){
            logger.debug("json does not exist ");
            return;
        }
        List<String> lines = FileUtil.readAllLines(path);

        logger.debug("Loading json");
        for (String line : lines){
            if(line.isEmpty()){
                continue;
            }

            Map.Entry<K,V> entry = JSON.parseObject(line, Map.Entry.class);

            cache.put(entry.getKey(),entry.getValue());
        }
        logger.debug("Loading completed");
    }
}
