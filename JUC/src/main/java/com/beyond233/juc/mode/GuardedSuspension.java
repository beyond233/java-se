package com.beyond233.juc.mode;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 描述: 同步模式之保护性暂停
 *
 * @author beyond233
 * @since 2021/1/23 20:55
 */
@Slf4j
public class GuardedSuspension {

    public static void main(final String[] args) {
        GuardedObject guardedObject = new GuardedObject(1);

        // 线程1：等待资源去干活
        new Thread(() -> {
            log.info("begin");
            Object result = guardedObject.get(2000);
            log.info("result = " + result);
        }, "t1").start();

        // 线程2：准备资源并唤醒等待资源的线程
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("notify");
            guardedObject.complete(new Object());
        }, "t2").start();


    }


}

@Slf4j
@Data
class GuardedObject {

    /**
     * id
     */
    private Integer id;

    /**
     * 模拟线程运行需要的资源
     */
    private Object resource;

    GuardedObject(Integer id) {
        this.id = id;
    }

    /**
     * 等待获取结果
     *
     * @param timeout 超时时间
     * @return {@link Object}
     * @author beyond233
     * @since 2021/1/23 23:37
     */
    public synchronized Object get(long timeout) {
        // 开始时间
        long startTime = System.currentTimeMillis();
        // 已经等待的时间
        long passedTime = 0;
        // 剩余还需要等待的时间
        long waitTime;

        // 1.不满足资源条件则进行wait，用while来防止虚假唤醒
        while (this.resource == null) {
            // 剩余还需要等待的时间 = 总的等待时间 - 已经等待的时间
            waitTime = timeout - passedTime;
            if (waitTime <= 0) {
                log.info(timeout + "s 等待时间已到!");
                break;
            }
            try {
                wait(waitTime);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            // 已经等待的时间 = 当前时间 - 开始时间
            passedTime = System.currentTimeMillis() - startTime;
        }

        // 2.满足资源条件则返回资源
        return this.resource;
    }

    /**
     * 模拟完成任务后设置处理结果，并唤醒等待线程
     *
     * @param result 处理结果
     * @author beyond233
     * @since 2021/1/23 23:37
     */
    public synchronized void complete(final Object result) {
        this.resource = result;
        notifyAll();
        log.info("唤醒所有wait线程");
    }

}