package com.beyond233.juc.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <p>项目文档: 加1计数器</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 16:40
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //加1计数，到达指定数则执行runnable接口中的方法
        CyclicBarrier barrier = new CyclicBarrier(7, () -> System.out.println("集齐7颗龙珠！召唤神龙！"));

        for (int i = 0; i < 7; i++) {
            int t = i;
            new Thread(()->{
                System.out.println("集齐"+(t+1)+"颗");
                try {
                    //await()方法作用：加1、阻塞线程，然后等待总数达到7时打断阻塞线程
                    barrier.await();
                } catch (InterruptedException |BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
