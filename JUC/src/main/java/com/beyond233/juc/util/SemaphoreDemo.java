package com.beyond233.juc.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *     项目文档: 信号量，信号标
 *     应用场景：限流
 * </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 16:59
 */
@Slf4j(topic = "semaphoreTest")
public class SemaphoreDemo {
    public static void main(String[] args) {
        //5个车位
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 5;   i++) {
            new Thread(() -> {
                try {
                    //acquire()：抢占车位,总车位减1
                    semaphore.acquire();
                    log.debug("抢占了一个车位,车位剩余："+semaphore.availablePermits());
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    //release()：释放车位、总车位加1
                    semaphore.release();
                    log.debug("释放了一个车位,车位剩余："+semaphore.availablePermits());
                }
            },String.valueOf(i)).start();
        }
    }
}
