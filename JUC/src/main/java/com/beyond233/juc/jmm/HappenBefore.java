package com.beyond233.juc.jmm;

import java.util.concurrent.TimeUnit;

/**
 * happens-before规则
 *
 * @author beyond233
 * @date 2021/1/29 9:38
 */
public class HappenBefore {
    static volatile int x;
    static int y;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            x = 10;
            y = 20;
        }, "t1").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            // 1.volatile保证可见性
            System.out.println(x);
            //2.对变量默认值（0，false，null）的写，对其它线程对该变量的读可见
            System.out.println(y);
        }, "t2").start();

    }
}
