package com.beyond233.juc.lock;

import com.beyond233.juc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 描述:AQS学习
 *
 * @author beyond233
 * @since 2021/2/7 22:42
 */
@Slf4j
public class AQSLearn {

    public static void main(String[] args) {
        MyLock lock = new MyLock();

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                lock.lock();
                log.info("lock");
                Sleeper.second(1);
                log.info("unlock");
                lock.unlock();
            }, "t" + i).start();
        }
    }


    /**
     * 自定义锁（不可重入锁）
     */
    static class MyLock implements Lock {

        private MySync sync = new MySync();

        /**
         * AQS实现独占锁同步器
         */
        class MySync extends AbstractQueuedSynchronizer {

            private static final long serialVersionUID = -2106799198215749249L;

            /**
             * 加锁状态： 1-已加锁；0-未加锁
             */
            private static final int STATE_LOCK = 1;
            private static final int STATE_UNLOCK = 0;

            /**
             * 尝试加锁
             */
            @Override
            protected boolean tryAcquire(int arg) {
                if (compareAndSetState(STATE_UNLOCK, STATE_LOCK)) {
                    // 加锁成功后设置owner为当前线程
                    setExclusiveOwnerThread(Thread.currentThread());
                    return true;
                }
                return false;
            }

            /**
             * 尝试释放锁
             */
            @Override
            protected boolean tryRelease(int arg) {
                setExclusiveOwnerThread(null);
                setState(STATE_UNLOCK);
                return true;
            }

            /**
             * 是否持有独占锁
             */
            @Override
            protected boolean isHeldExclusively() {
                return getState() == STATE_LOCK;
            }

            /**
             * 获取一个条件变量
             */
            public Condition newCondition() {
                return new ConditionObject();
            }
        }

        /**
         * 加锁
         */
        @Override
        public void lock() {
            sync.acquire(1);
        }

        /**
         * 加锁（可打断）
         */
        @Override
        public void lockInterruptibly() throws InterruptedException {
            sync.acquireInterruptibly(1);
        }

        /**
         * 尝试加锁
         */
        @Override
        public boolean tryLock() {
            return sync.tryAcquire(1);
        }

        /**
         * 超时尝试加锁
         */
        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return sync.tryAcquireNanos(1, unit.toNanos(time));
        }

        /**
         * 释放锁
         */
        @Override
        public void unlock() {
            sync.release(1);
        }

        /**
         * 返回条件变量
         */
        @Override
        public Condition newCondition() {
            return sync.newCondition();
        }
    }

}
