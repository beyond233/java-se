package com.beyond233.juc.util;

import java.util.concurrent.CountDownLatch;

/**
 * <p>项目文档:  CountDownLatch倒数计数器</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 16:32
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " out");
                //减1
                latch.countDown();
            }, String.valueOf(i)).start();
        }

        //等待latch归0:即等待线程都执行结束
        latch.await();

        System.out.println("close");

    }
}
