package com.beyond233.juc.jmm;

import java.util.concurrent.TimeUnit;

/**
 * <p>项目文档: 死循环</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-18 22:03
 */
public class DeadLoop {
    static boolean run = true;
    //volatile可以保证共享变量在多线程间的可见性
//    volatile static boolean run = true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            while (run){
            }
        }).start();

        TimeUnit.SECONDS.sleep(1);
        //主线程中对run的修改对于其他线程是不可见的，所以在其他线程中run依然是true
        run = false;
    }

}
