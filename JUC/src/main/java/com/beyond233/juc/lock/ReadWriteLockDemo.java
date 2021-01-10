package com.beyond233.juc.lock;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>项目文档:读写锁
 *
 *    Synchronized存在明显的一个性能问题就是读与读之间互斥，简言之就是，我们编程想要实现的最好效果是，
 *    可以做到读和读互不影响，读和写互斥，写和写互斥，提高读写的效率,这时使用ReadWriteLock读写锁可以实现此效果
 *
 * </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 17:44
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
//        MyCache cache = new MyCache();
        MyCacheWithLock cache = new MyCacheWithLock();

        for (int i = 0; i < 30; i++) {
            int t = i;
            new Thread(()->cache.write(t),String.valueOf(t)).start();
        }

        for (int i = 0; i < 30; i++) {
            int t = i;
            new Thread(() -> cache.read(t),String.valueOf(t)).start();
        }


    }
}


class MyCache{
    private volatile HashMap<Integer, Integer> map = new HashMap<>(5);

    /**写*/
    public void write(int i){
        System.out.println(Thread.currentThread().getName()+" 写 ");
        map.put(i, i);
        System.out.println(Thread.currentThread().getName()+" 写  OK");
    }
    /**读*/
    public void read(int i){
        System.out.println(Thread.currentThread().getName()+" 读 ");
        map.get(i);
        System.out.println(Thread.currentThread().getName()+" 读  OK");
    }
}


class MyCacheWithLock{
    private volatile HashMap<Integer, Integer> map = new HashMap<>(5);

    /**添加读写锁*/
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 写
     * 写的时候只允许一个线程写，即写与写互斥： 添加写锁
     * */
    public void write(int i){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+" 写 ");
            map.put(i, i);
            System.out.println(Thread.currentThread().getName()+" 写  OK");
        }finally{
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 读
     * 读的时候允许多个线程读，即读与读不互斥： 添加读锁
     * */
    public void read(int i){
        readWriteLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+" 读 ");
            map.get(i);
            System.out.println(Thread.currentThread().getName()+" 读  OK");
        }finally{
            readWriteLock.readLock().unlock();
        }
    }
}