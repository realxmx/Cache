package org.cache.api;

import java.util.concurrent.TimeUnit;

public interface IPersist {
    public void setCheckTime(int timeNum, TimeUnit timeUnit);
    public void cachePersist();
}
