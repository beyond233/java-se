package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 11:20
 */
@Slf4j(topic = "yield()和优先级")
public class YieldAndPriority {
    public static void main(String[] args) {
        Runnable r1 = () -> {
            int count = 0;
            for (; ; ) {
                System.out.println("1->" + count++);
            }
        };

        Runnable r2 = () -> {
            int count = 0;
            for (;;){
                //运行yield会将线程t2的时间片让出，从running转变为runnable
//                Thread.yield();
                System.out.println("              2->"+count++);
            }
        };

        Thread t1 = new Thread(r1, "t1");
        Thread t2 = new Thread(r2, "t2");

        //设置线程优先级
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);

        t1.start();
        t2.start();
    }
}
