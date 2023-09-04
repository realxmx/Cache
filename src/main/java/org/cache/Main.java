package org.cache;

import org.cache.core.Cache;

import java.util.Map;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;


public class Main {
    public static void main(String[] args) {

        Cache<Integer, Integer> cache = new Cache<Integer, Integer>()
                .enableEvict(5)
                .enableExpire(10,MINUTES)
                .enablePersist("src/main/resources/persist.json",10,SECONDS)
                .enableLoad("src/main/resources/persist.json");

        Set<Map.Entry<Integer, Integer>> set;
        for (int i = 0; i < 8; i++) {
            cache.put(i, i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("中断");
            }
        }

        /*Set<Map.Entry<Integer, Integer>> set;
        for (int i = 0; i < 5; i++)
            cache.put(i, i, i, TimeUnit.SECONDS);

        for (int i = 0; i < 5; i++) {

            set = cache.getAll();
            for (Map.Entry<Integer, Integer> s : set) {
                System.out.println("遍历:" + s);
            }
            cache.get(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("出现中断");
            }
        }*/

        /*cache.has(1);
        System.out.println("get:"+cache.get(1));
        cache.remove(1);*/


    }
}