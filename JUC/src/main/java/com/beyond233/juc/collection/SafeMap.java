package com.beyond233.juc.collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>项目文档: 多线程操作map可能会发生java.util.ConcurrentModificationException并发修改异常</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 16:13
 */
public class SafeMap {
    public static void main(String[] args) {
//        HashMap<Integer, Integer> map = new HashMap<>(10);
        //解决方案一
//        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>(20);
        //解决方案二
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>(20));

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                map.put(new Random().nextInt(),1);
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
