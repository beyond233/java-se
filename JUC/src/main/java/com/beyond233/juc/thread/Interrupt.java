package com.beyond233.juc.thread;

import java.util.concurrent.TimeUnit;

/**
 * 描述: interrupt()方法学习
 *
 * @author beyond233
 * @since 2021/1/10 22:41
 */
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                // 1.获取打断标记
                boolean interrupted = Thread.currentThread().isInterrupted();
                // 2.判断是否被打断
                if (interrupted) {
                    System.out.println("interrupted ,then break");
                    break;
                }
            }
        },"t1");

        t1.start();

        TimeUnit.SECONDS.sleep(1);
        System.out.println("interrupt thread t1");
        t1.interrupt();
    }
}
