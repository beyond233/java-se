package com.beyond233.juc.threadpool;

import com.beyond233.juc.util.Sleeper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 *
 * @author beyond233
 * @date 2021/2/5 16:51
 */
@Slf4j(topic = "ThreadPool")
public class MyThreadPool {
    /**
     * 核心线程数
     */
    private int coreSize;
    /**
     * 任务队列
     */
    private MyBlockingQueue<Runnable> taskQueue;
    /**
     * 工作线程集合
     */
    private final HashSet<Worker> workers = new HashSet<>();
    /**
     * 获取任务时的超时时间
     */
    private long timeout;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 任务队列已满时对新任务的拒绝策略
     */
    private RejectPolicy<Runnable> rejectPolicy;


    public MyThreadPool(int coreSize, int taskQueueCapacity, int timeout,
                        TimeUnit timeUnit, RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.taskQueue = new MyBlockingQueue<>(taskQueueCapacity);
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 执行任务：  1.当任务数没有超过 coreSize 时，直接交给 worker 对象执行
     * 2.如果任务数超过 coreSize 时，加入任务队列暂存
     *
     * @param task 要执行的任务
     * @since 2021/2/6 22:27
     */
    public void execute(Runnable task) {
        synchronized (workers) {
            // 1.若线程池中线程数量未达到核心线程数则创建新线程去执行任务
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            } else {
                // 2.若核心线程数已达到则尝试将任务加入队列中，若失败则使用对应拒绝策略进行处理
                // the following is TODO
                // 1) 死等
                // 2) 带超时等待
                // 3) 让调用者放弃任务执行
                // 4) 让调用者抛出异常
                // 5) 让调用者自己执行任务
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    /**
     * 工作线程：用用于执行任务
     */
    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        /**
         * 执行任务：
         * 1) 当 task 不为空，执行任务
         * 2) 当 task 执行完毕，再接着从任务队列获取任务并执行
         *
         * @since 2021/2/6 22:33
         */
        @Override
        public void run() {
            while (task != null || (task = taskQueue.take(timeout, timeUnit)) != null) {
                try {
                    log.info("正在执行任务: {}", task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 每次执行完当前任务将其置为null才可以继续去任务队列中获取新的任务
                    task = null;
                }
            }
            // 任务队列中的任务已全部执行完成时移除工作线程
            synchronized (workers) {
                log.info("worker工作线程已被移除!,被移除线程: {}", this);
                workers.remove(this);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Worker worker = (Worker) o;
            return task.equals(worker.task);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task);
        }
    }
}

/**
 * 自定义拒绝策略
 */
@FunctionalInterface
interface RejectPolicy<T> {
    void reject(MyBlockingQueue<T> queue, T task);
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
     * 超时获取
     *
     * @param timeout  超时时间
     * @param timeUnit 时间单位
     * @return {@link T}
     * @author beyond233
     * @since 2021/2/6 20:31
     */
    public T take(long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            // 将 timeout 统一转换为 纳秒
            long awaitNanos = timeUnit.toNanos(timeout);
            // 1.队列为空则阻塞等待
            while (taskQueue.isEmpty()) {
                // 1.1判断超时时间是否已到
                if (awaitNanos <= 0) {
                    return null;
                }
                try {
                    awaitNanos = emptyQueueWait.awaitNanos(awaitNanos);
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

    /**
     * 超时添加任务
     */
    public boolean put(T task, long timeout, TimeUnit timeUnit) {
        lock.lock();
        try {
            long awaitNanos = timeUnit.toNanos(timeout);
            while (taskQueue.size() == capacity) {
                if (awaitNanos <= 0) {
                    return false;
                }
                try {
                    awaitNanos = fullQueueWait.awaitNanos(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            taskQueue.addLast(task);
            fullQueueWait.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 尝试将任务加入队列中，若失败则使用指定的拒绝策略进行处理
     */
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
            // 队列已满则使用拒绝策略处理
            if (taskQueue.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else {
                // 队列未满则将任务加入任务队列中
                taskQueue.addLast(task);
                emptyQueueWait.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}

@Slf4j(topic = "Test")
class Test {
    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool(1, 1, 1000, TimeUnit.MILLISECONDS
                , (queue, task) -> {
            // 1. 死等
            // queue.put(task);
            // 2) 带超时等待
            // queue.offer(task, 1500, TimeUnit.MILLISECONDS);
            // 3) 让调用者放弃任务执行
            // log.debug("放弃{}", task);
            // 4) 让调用者抛出异常
            throw new RuntimeException("任务执行失败 " + task);
            // 5) 让调用者自己执行任务
//            task.run();
        });

        for (int i = 0; i < 4; i++) {
            int index = i;
            pool.execute(() -> {
                Sleeper.millisecond(500);
                log.info("task【{}】", index);
            });
        }

    }
}