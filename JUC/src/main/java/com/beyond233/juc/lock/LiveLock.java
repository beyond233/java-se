package com.beyond233.juc.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>项目文档: 活锁测试</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-17 23:01
 */
@Slf4j(topic ="活锁测试")
public class LiveLock {
    static volatile int count=10;
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (count>0){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                log.debug("count:{}",count);
            }
        },"t1").start();

        new Thread(() -> {
            while (count<20){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                log.debug("count:{}",count);
            }
        },"t2").start();

    }



}
