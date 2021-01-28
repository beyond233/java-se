package com.beyond233.juc.practice;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步模式之顺序控制： 实现三个线程交替打印abc字母
 *
 * @author beyond233
 * @date 2021/1/28 11:23
 */
public class SequentialController {
    public static void main(String[] args) throws InterruptedException {
        // 1.wait、notify实现顺序控制
//        SynWaitNotify synWaitNotify = new SynWaitNotify(1, 3);
//        new Thread(() -> synWaitNotify.print(1, 2, "a")).start();
//        new Thread(() -> synWaitNotify.print(2, 3, "b")).start();
//        new Thread(() -> synWaitNotify.print(3, 1, "c")).start();

        // 2.ReentrantLock实现顺序控制
        LockCondition lockCondition = new LockCondition(3);
        Condition aWait = lockCondition.newCondition();
        Condition bWait = lockCondition.newCondition();
        Condition cWait = lockCondition.newCondition();
        new Thread(() -> lockCondition.print(aWait, bWait, "a")).start();
        new Thread(() -> lockCondition.print(bWait, cWait, "b")).start();
        new Thread(() -> lockCondition.print(cWait, aWait, "c")).start();

        TimeUnit.SECONDS.sleep(1);
        lockCondition.start(aWait);
    }
}

/**
 * ReentrantLock实现顺序控制： 先让各个线程按照不同条件wait，然后再手动唤醒第一个条件，执行完业务后再唤醒下一个条件
 */
class LockCondition extends ReentrantLock {
    private static final long serialVersionUID = -7585100636789313419L;
    private int loopNumber;

    LockCondition(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void start(Condition first) {
        this.lock();
        try {
            first.signal();
        } finally {
            this.unlock();
        }
    }

    public void print(Condition now, Condition next, String context) {
        for (int i = 0; i < this.loopNumber; i++) {
            lock();
            try {
                now.await();
                System.out.print(context);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                unlock();
            }
        }
    }

}

/**
 * wait、notify实现顺序控制
 */
class SynWaitNotify {
    private int flag;
    private int loopNumber;

    public SynWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /**
     * 循环交替打印
     *
     * @param now     当前flag
     * @param next    下一个flag
     * @param context 打印的内容
     * @author beyond233
     * @date 2021/1/28  11:46
     */
    public void print(int now, int next, String context) {
        for (int i = 0; i < this.loopNumber; i++) {
            synchronized (this) {
                while (flag != now) {
                    try {
                        wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(context);
                flag = next;
                notifyAll();
            }
        }
    }
}