package com.beyond233.juc.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>项目文档:  多线程操作list集合时 可能会发生java.util.ConcurrentModificationException并发修改异常 </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 14:03
 */
@Slf4j
public class SafeArrayList {
    public static void main(String[] args) {
//        ArrayList<Integer> list = new ArrayList<>();
        //解决方法一：写入时复制
//        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        //解决方法二：
        List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                list.add(new Random().nextInt());
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
