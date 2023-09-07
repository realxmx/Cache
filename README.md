# Cache
实现一个简单通用的缓存框架，研究内存数据存储的实现与设计
## 框架构造
- 泛型化设计，不同业务维度可以通用
- 标准化接口，满足多场景的使用诉求
- 多策略可选，允许选择不同实现策略甚至是缓存存储机制
## 特性实现
- 使用 fluent 流式配置
- 支持 cache 固定大小
- 支持自定义 map 实现策略
- 支持 expire 过期特性
- 支持 FIFO 和 LRU 驱除策略
- 支持 load 初始化和 persist 持久化
- 整合日志框架
## ICache 接口
| 接口名称 | 含义说明 |
|:---|:---|
| enableEvict | 配置淘汰策略 |
| enableExpire | 配置过期策略 |
| enablePersist | 配置持久化方法 |
| enableLoad | 配置初始化方法 |
| has | 判断缓存中是否有指定的值 |
| remove | 将指定的缓存记录删除 |
| get | 根据键查询对应的值 |
| put | 将数据添加到缓存中 |
| setExpire | 设置缓存过期时间 |
| getValues | 根据多个键查询多个值 |
| getAllKeys | 获取所有键 |
| getAllValues | 获取所有值 |
| getAll | 获取所有数据 |
## 测试
```java
Cache<Integer, Integer> cache = new Cache<Integer, Integer>()
                .enableEvict(3)
                .enableExpire(10,MINUTES)
                .enablePersist("src/main/resources/persist.json",10,SECONDS)
                .enableLoad("src/main/resources/persist.json");

        Set<Map.Entry<Integer, Integer>> set;
        for (int i = 0; i < 5; i++) {
            cache.put(i, i,i,SECONDS);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("中断");
            }
        }
        System.out.println(cache.get(1));
```
## 运行日志
```
19:06:56.818 [main] DEBUG org.cache.load.LoadJson -- Loading json
19:06:56.940 [main] DEBUG org.cache.core.Cache -- put key:3
19:06:56.941 [main] DEBUG org.cache.core.Cache -- put key:4
19:06:56.941 [main] DEBUG org.cache.load.LoadJson -- Loading completed
19:06:56.941 [main] DEBUG org.cache.core.Cache -- put key:0 and set time
19:06:57.822 [Timer-0] DEBUG org.cache.load.LoadJson -- Timely expire
19:06:57.822 [Timer-0] DEBUG org.cache.core.Cache -- remove key:0
19:06:57.822 [Timer-0] DEBUG org.cache.load.LoadJson -- Timely expire completed
19:06:57.823 [Timer-1] DEBUG org.cache.load.LoadJson -- Persisting json
19:06:57.830 [Timer-1] DEBUG org.cache.load.LoadJson -- Persisting completed
19:06:57.946 [main] DEBUG org.cache.core.Cache -- put key:1 and set time
19:06:58.947 [main] DEBUG org.cache.core.Cache -- remove key:3
19:06:58.947 [main] DEBUG org.cache.evict.EvictLRU -- evict eldest key
19:06:58.947 [main] DEBUG org.cache.core.Cache -- put key:2 and set time
19:06:59.949 [main] DEBUG org.cache.core.Cache -- remove key:4
19:06:59.949 [main] DEBUG org.cache.evict.EvictLRU -- evict eldest key
19:06:59.949 [main] DEBUG org.cache.core.Cache -- put key:3 and set time
19:07:00.950 [main] DEBUG org.cache.core.Cache -- remove key:1
19:07:00.950 [main] DEBUG org.cache.evict.EvictLRU -- evict eldest key
19:07:00.950 [main] DEBUG org.cache.core.Cache -- put key:4 and set time
19:07:01.952 [main] DEBUG org.cache.load.LoadJson -- Lazy expire
19:07:01.952 [main] DEBUG org.cache.core.Cache -- remove key:1
19:07:01.952 [main] DEBUG org.cache.core.Cache -- remove key:2
19:07:01.952 [main] DEBUG org.cache.evict.EvictLRU -- evict eldest key
19:07:01.952 [main] DEBUG org.cache.core.Cache -- get key:1
null
19:07:02.835 [Timer-0] DEBUG org.cache.load.LoadJson -- Timely expire
19:07:02.835 [Timer-0] DEBUG org.cache.core.Cache -- remove key:2
19:07:02.835 [Timer-0] DEBUG org.cache.load.LoadJson -- Timely expire completed
19:07:07.823 [Timer-1] DEBUG org.cache.load.LoadJson -- Persisting json
19:07:07.823 [Timer-1] DEBUG org.cache.load.LoadJson -- Persisting completed
19:07:07.835 [Timer-0] DEBUG org.cache.load.LoadJson -- Timely expire
19:07:07.835 [Timer-0] DEBUG org.cache.core.Cache -- remove key:3
19:07:07.835 [Timer-0] DEBUG org.cache.core.Cache -- remove key:4
19:07:07.835 [Timer-0] DEBUG org.cache.load.LoadJson -- Timely expire completed
```
