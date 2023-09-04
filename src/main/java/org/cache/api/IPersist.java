package org.cache.api;

import java.util.concurrent.TimeUnit;

public interface IPersist {
    void setCheckTime(int timeNum, TimeUnit timeUnit);
    void cachePersist();
}
