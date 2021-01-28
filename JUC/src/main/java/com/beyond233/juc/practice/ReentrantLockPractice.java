package com.beyond233.juc.practice;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述: ReentrantLock练习
 *
 * @author beyond233
 * @since 2021/1/27 23:17
 */
@Slf4j(topic = "-")
public class ReentrantLockPractice {
    static boolean hasMilkTea = false;
    static boolean hasCoffee = false;

    static ReentrantLock lock = new ReentrantLock();
    static Condition waitMilkTea = lock.newCondition();
    static Condition waitCoffee = lock.newCondition();

    public static void main(String[] args) {
        // 1.小学生要喝奶茶
        new Thread(() -> {
            lock.lock();
            try {
                log.info("有奶茶吗？ {}", hasMilkTea);
                while (!hasMilkTea) {
                    waitMilkTea.await();
                }
                log.info("喝饱了奶茶，可以开始学习了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "小学生").start();

        // 2.打工人要喝咖啡
        new Thread(() -> {
            lock.lock();
            try {
                log.info("有咖啡吗？ {}", hasCoffee);
                while (!hasCoffee) {
                    waitCoffee.await();
                }
                log.info("喝饱了咖啡，可以开始打工了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "打工人").start();

        // 3.漂亮妹子送奶茶了
        new Thread(() -> {
            lock.lock();
            try {
                log.info("送奶茶了");
                hasMilkTea = true;
                waitMilkTea.signal();
            } finally {
                lock.unlock();
            }
        }, "漂亮妹子").start();

        // 4.老板送咖啡了
        new Thread(() -> {
            lock.lock();
            try {
                log.info("送咖啡了");
                hasCoffee = true;
                waitCoffee.signal();
            } finally {
                lock.unlock();
            }
        }, "老板").start();

    }
}
