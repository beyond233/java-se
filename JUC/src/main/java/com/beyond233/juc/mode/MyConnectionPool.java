package com.beyond233.juc.mode;

import com.beyond233.juc.util.Sleeper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 描述: 利用享元模式实现数据库连接池
 *
 * @author beyond233
 * @since 2021/2/1 22:02
 */
public class MyConnectionPool {
    /**
     * 连接池大小
     */
    private int poolSize;
    /**
     * 连接池
     */
    private MyConnection[] connections;
    /**
     * 记录连接池中每一个connection连接的使用状态：1-已被使用；0-空闲
     */
    private AtomicIntegerArray states;
    private static final Integer STATE_BUSY = 1;
    private static final Integer STATE_FREE = 0;


    public MyConnectionPool(int poolSize) {
        this.poolSize = poolSize;
        connections = new MyConnection[poolSize];
        states = new AtomicIntegerArray(new int[poolSize]);
        for (int i = 0; i < connections.length; i++) {
            connections[i] = new MyConnection(i);
        }
    }

    /**
     * 获取一个空闲的连接
     */
    public MyConnection get() {
        while (true) {
            // 1.从连接池中获取一个空闲的连接，并将该连接对应的状态修改为已使用
            for (int i = 0; i < poolSize; i++) {
                if (STATE_FREE.equals(states.get(i)) && states.compareAndSet(i, STATE_FREE, STATE_BUSY)) {
                    return connections[i];
                }
            }
            // 2.若连接池中已无空闲连接，则让线程wait阻塞等待空闲连接
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 释放已获取连接：将对应连接状态修改为空闲，并唤醒等待的线程
     */
    public void release(MyConnection connection) {
        for (int i = 0; i < poolSize; i++) {
            if (connections[i] == connection) {
                states.set(i, STATE_FREE);
                synchronized (this) {
                    this.notifyAll();
                }
                break;
            }
        }
    }
}

/**
 * 模拟Connection连接实现类
 */
@Data
@AllArgsConstructor
@ToString
class MyConnection {
    private int id;

    public MyConnection() {
        // 模拟构造连接需要花费的时间
        Sleeper.millisecond(1);
    }
}

@Slf4j(topic = "-")
class Test {
    public static void main(String[] args) {
        MyConnectionPool pool = new MyConnectionPool(10);
        ArrayList<Thread> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(new Thread(() -> {
                MyConnection connection = pool.get();
                log.info(connection.toString());
                Sleeper.millisecond(3);
                pool.release(connection);
            }, "t" + i));
        }

        long start = System.currentTimeMillis();
        list.forEach(Thread::start);
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("cost: " + (System.currentTimeMillis() - start));


    }
}