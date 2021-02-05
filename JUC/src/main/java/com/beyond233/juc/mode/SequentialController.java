package com.beyond233.juc.mode;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
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
//        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1, 3);
//        new Thread(() -> syncWaitNotify.print(1, 2, "a")).start();
//        new Thread(() -> syncWaitNotify.print(2, 3, "b")).start();
//        new Thread(() -> syncWaitNotify.print(3, 1, "c")).start();

        // 2.ReentrantLock实现顺序控制
//        SyncAwaitSignal lock = new SyncAwaitSignal(3);
//        Condition aWait = lock.newCondition(),
//                bWait = lock.newCondition(),
//                cWait = lock.newCondition();
//        new Thread(() -> lock.print(aWait, bWait, "a")).start();
//        new Thread(() -> lock.print(bWait, cWait, "b")).start();
//        new Thread(() -> lock.print(cWait, aWait, "c")).start();
//
//        TimeUnit.SECONDS.sleep(1);
//        lock.start(aWait);

        // 3.park、unPark实现顺序控制
        SyncPark syncPark = new SyncPark(3);
        Thread a = new Thread(() -> syncPark.print("a"));
        Thread b = new Thread(() -> syncPark.print("b"));
        Thread c = new Thread(() -> syncPark.print("c"));
        syncPark.setThreads(a, b, c);
        TimeUnit.SECONDS.sleep(1);
        syncPark.start();
    }
}

/**
 * park、unPark实现顺序控制
 */
class SyncPark {
    private int loopNumber;
    private Thread[] threads;

    SyncPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void setThreads(Thread... threads) {
        this.threads = threads;
    }

    public void print(String context) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(context);
            LockSupport.unpark(nextThread());
        }
    }

    public Thread nextThread() {
        int index = 0;
        Thread currentThread = Thread.currentThread();
        for (int i = 0; i < threads.length; i++) {
            if (currentThread == threads[i]) {
                index = i;
                break;
            }
        }

        if (index < threads.length - 1) {
            return threads[index + 1];
        } else {
            return threads[0];
        }
    }

    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
        LockSupport.unpark(threads[0]);
    }

}


/**
 * ReentrantLock实现顺序控制： 先让各个线程按照不同条件wait，然后再手动唤醒第一个条件，执行完业务后再唤醒下一个条件
 */
class SyncAwaitSignal extends ReentrantLock {
    private static final long serialVersionUID = -7585100636789313419L;
    private int loopNumber;

    SyncAwaitSignal(int loopNumber) {
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
class SyncWaitNotify {
    private int flag;
    private int loopNumber;

    public SyncWaitNotify(int flag, int loopNumber) {
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