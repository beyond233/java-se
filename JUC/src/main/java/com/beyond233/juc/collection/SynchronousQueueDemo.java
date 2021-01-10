package com.beyond233.juc.collection;

import java.util.concurrent.SynchronousQueue;

/**
 * <p>项目文档:同步队列 </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-22 22:00
 */
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+" put 1");
                queue.put(1);
                System.out.println(Thread.currentThread().getName()+" put 2");
                queue.put(2);
                System.out.println(Thread.currentThread().getName()+" put 3");
                queue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName()+" take 1");
                queue.take();
                System.out.println(Thread.currentThread().getName()+" take 2");
                queue.take();
                System.out.println(Thread.currentThread().getName()+" take 3");
                queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"t2").start();


    }




    public static void test1(){

    }
}
