package com.beyond233.juc.cas;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-19 16:15
 */
public class Account {
    /**余额*/
    private AtomicInteger balance;

    public Account(Integer balance){
        this.balance = new AtomicInteger(balance);
    }
    /**
     * 获取余额
     * */
    public Integer get(){
        return balance.get();
    }
    /**
     * 修改余额
     * @param amount 需要减少的值
     * */
    public void withDraw(Integer amount){
//        while (true) {
//            //余额原先的值
//            int prv = balance.get();
//            //修改后的值：余额最新的值
//            int next = prv - amount;
//            if (balance.compareAndSet(prv,next)) {
//                break;
//            }
//        }
        balance.updateAndGet(balance -> balance - amount);
        System.out.println(balance.get());
    }
}

class CasTest{
    public static void main(String[] args) {
        Account account = new Account(1000);
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Thread(() -> account.withDraw(10)));
        }
        //当前时间
        long start = System.nanoTime();
        list.forEach(Thread::start);
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //结束时间
        long end = System.nanoTime();

        System.out.println("余额："+account.get());
        System.out.println("耗时："+(end-start)/1000_000+"ms");
    }
}
