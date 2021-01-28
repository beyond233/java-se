package com.beyond233.juc.thread;

/**
 *  Thread and Thread Frames
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-07 16:34
 */
public class Frames {
    public static void main(String[] args) {
        // 1. t1 thread
        new Thread(() -> method1(1), "t1").start();
        // 2. main thread
        method1(2);
    }

    public static void method1(int x){
        int y = x + 1;
        System.out.println(y);
        Object o = method2();
        System.out.println(o);
    }

    public static Object method2(){
        return new Object();
    }
}
