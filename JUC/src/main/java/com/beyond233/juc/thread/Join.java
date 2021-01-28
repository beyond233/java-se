package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 11:40
 */
@Slf4j(topic = "join().Test")
public class Join {

    public static int a = 0;
    public static int b = 0;

    public static void main(String[] args) throws InterruptedException {
        t1();
        log.debug("获取a: "+a);
    }

    public static void t1() throws InterruptedException {
        Runnable r1 = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a = 10;
            log.debug("t1结束");
        };
        Runnable r2 = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            a = 20;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("t2结束");
        };

        Thread t1 = new Thread(r1,"t1");
        Thread t2 = new Thread(r2,"t2");
        t1.start();
        t2.start();

        log.debug("t1 join 开始");
        //等待t1线程结束
        t1.join();
        log.debug("t1 join 结束");

        log.debug("t2 join 开始");
        //等待t2线程结束:只等待2秒
        t2.join(2000);
        log.debug("t2 join 结束");
    }
}
