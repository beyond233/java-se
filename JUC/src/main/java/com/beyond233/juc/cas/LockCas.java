package com.beyond233.juc.cas;

import com.beyond233.juc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述: 使用cas实现锁
 *
 * @author beyond233
 * @since 2021/1/30 11:00
 */
@Slf4j
public class LockCas {

    /**
     * lockState加锁状态： 0-未加锁；1-已加锁
     */
    private AtomicInteger lockState = new AtomicInteger(STATE_UNLOCK);
    private static Integer STATE_LOCKED = 1;
    private static Integer STATE_UNLOCK = 0;

    /**
     * 加锁： 线程t1使用cas将lockState的值从0修改为1则表示t1获取锁成功，其他线程不能将lockState从0改为1则一直陷入循环表示加锁失败
     * 线程陷入循环后性能很差，不可用于实际
     */
    public void lock() {
        while (true) {
            if (lockState.compareAndSet(STATE_UNLOCK, STATE_LOCKED)) {
                break;
            }
        }
    }

    /**
     * 修改lockState值为0则表示解锁成功，此方法只能加锁成功的线程调用，所以可以不用cas修改
     */
    public void unlock() {
        lockState.set(STATE_UNLOCK);
    }

    public static void main(String[] args) {
        LockCas lock = new LockCas();

        new Thread(() -> {
            lock.lock();
            log.info("拿到锁,先睡会儿觉");
            Sleeper.second(1);
            lock.unlock();
        }, "t1").start();

        new Thread(() -> {
            lock.lock();
            log.info("拿到锁了");
            lock.unlock();
        }, "t2").start();
    }

}
