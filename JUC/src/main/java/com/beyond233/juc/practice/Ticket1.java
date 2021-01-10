package com.beyond233.juc.practice;


import lombok.extern.slf4j.Slf4j;
import sun.security.krb5.internal.Ticket;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-20 20:48
 */
@Slf4j(topic = "Ticket1")
public class Ticket1 {
    /**票的总量*/
    private volatile static int count=0;
    /**买到票的数量*/
    private volatile static int amount=0;


    /**
     * 售票方法
     * */
    public void sale() {
        while (true) {
            synchronized (this){
                while (count==0) {
                    //没有票，等待
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count--;
                amount++;
                log.debug(String.valueOf(count));
                //提醒添加票
                notifyAll();
            }
        }
    }
    /**
     * 添加票
     * */
    public void add() {
        while (true) {
            synchronized (this){
                while (count>0) {
                    //还有票就不添加
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                count++;
                log.debug(String.valueOf(count));
                //提醒卖票
                notifyAll();
            }
        }
    }

    /**
     * 退票
     * */
    public void cancel(){
        while (true) {
            synchronized (this){
                while (amount==0){
                    try {
                        //没买到票就等待
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                amount--;
                count++;
                log.debug(String.valueOf(amount));
                notifyAll();
            }
        }

    }


    /**
     * 返回当前票的总数
     * */
    public static int getCount(){
        return count;
    }
}

@Slf4j(topic = "Ticket2")
class Ticket2 {
    /**票的总量*/
    private static volatile int count=0;
    /**已经买到的票*/
    private static volatile int amount=0;

    private Lock lock = new ReentrantLock();

    private Condition saleCond = lock.newCondition();
    private Condition addCond = lock.newCondition();
    private Condition cancelCond = lock.newCondition();

    /**
     * 售票方法
     * */
    public void sale(){
        while (true) {
            lock.lock();
            try {
                while (amount>=10||count==0) {
                    try {
                        saleCond.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //卖票
                count--;
                amount++;
                log.debug("总票数："+ count +" 持有数："+amount);
                //通知可以退票了
                cancelCond.signal();
            }finally{
                lock.unlock();
            }
        }
    }
    /**
     * 添加票
     * */
    public void add(){
        while (true) {
            lock.lock();
            try{
                if (count==10) {
                    break;
                }
                while (count>10){
                    try {
                        addCond.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (count==0) {
                    log.debug("开始加票啦");
                }
                //添加票
                count++;
                log.debug("总票数："+ count +" 持有数："+amount);
                //通知可以卖票了
                saleCond.signalAll();
            }finally{
                lock.unlock();
            }
        }
    }
    /**
     * 取消票
     **/
    public void cancel(){
        while (true) {
            lock.lock();
            try {
                while (amount==0){
                    try {
                        cancelCond.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //取消
                amount--;
                count++;
                log.debug("总票数："+ count +" 持有数："+amount);
                //通知可以卖票了
                addCond.signalAll();
            }finally{
                lock.unlock();
            }
        }
    }

    /**
     * 返回当前票的总数
     * */
    public static int getCount(){
        return count;
    }
}

@Slf4j()
class Test{
    public static void main(String[] args) throws InterruptedException {
//        Ticket2 ticket = new Ticket2();
//        new Thread(()-> {
//            for (int i = 0; i < 40; i++) {
//                ticket.sale();
//            }
//        },"t1").start();
//        new Thread(()-> {
//            for (int i = 0; i < 40; i++) {
//                ticket.sale();
//            }
//        },"t2").start();
//        new Thread(()-> {
//            for (int i = 0; i < 20; i++) {
//                ticket.sale();
//            }
//        },"t3").start();


        //生产者消费者模式
        Ticket2 ticket = new Ticket2();
        new Thread(() -> ticket.sale(),"卖1").start();
        new Thread(() -> ticket.add(),"加1").start();
        new Thread(() -> ticket.cancel(),"退 ").start();
    }
}
