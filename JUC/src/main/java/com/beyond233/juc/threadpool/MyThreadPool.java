package com.beyond233.juc.threadpool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 *
 * @author beyond233
 * @date 2021/2/5 16:51
 */
public class MyThreadPool {
    /**
     * 核心线程数
     */
    private int coreSize;
    /**
     * 任务队列
     */
    private MyBlockingQueue taskQueue;

}

/**
 * 自定义阻塞队列： 用来存储线程需要执行的任务
 */
class MyBlockingQueue<T> {
    /**
     * 任务队列
     */
    private Deque<T> taskQueue = new ArrayDeque<>();
    /**
     * 队列容量
     */
    private int capacity;
    /**
     * 锁
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * 任务消费者阻塞条件：队列为空
     */
    private Condition emptyQueueWait = lock.newCondition();
    /**
     * 任务生产者阻塞条件：队列已满
     */
    private Condition fullQueueWait = lock.newCondition();

    MyBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 返回队列容量
     */
    public int size() {
        lock.lock();
        try {
            return taskQueue.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 从队列中获取任务
     */
    public T take() {
        lock.lock();
        try {
            // 1.队列为空则阻塞等待
            while (taskQueue.isEmpty()) {
                try {
                    emptyQueueWait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 2.不为空则从头部获取一个任务并唤醒wait的生产者线程
            T t = taskQueue.removeFirst();
            fullQueueWait.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 向队列中添加任务
     */
    public void put(T task) {
        lock.lock();
        try {
            // 1.队列已满则阻塞等待
            while (taskQueue.size() == capacity) {
                try {
                    fullQueueWait.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 2.向队列尾部添加一个任务并唤醒wait的消费者线程
            taskQueue.addLast(task);
            emptyQueueWait.signal();
        } finally {
            lock.unlock();
        }
    }
}
