package com.beyond233.juc.lock;

import lombok.AllArgsConstructor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>项目文档: 用三个线程实现：a打印5次，b打印5次，c打印5次，最后效果为：abcabcabcabcabc </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-18 16:03
 */
public class LoopPrintDemo {
    public static void main(String[] args) throws InterruptedException {
        AwaitSignal as = new AwaitSignal(5);
        Condition a = as.newCondition();
        Condition b = as.newCondition();
        Condition c = as.newCondition();

        new Thread(() -> as.print("a", a, b)).start();
        new Thread(() -> as.print("b", b, c)).start();
        new Thread(() -> as.print("c", c, a)).start();

        TimeUnit.SECONDS.sleep(1);
        as.lock();
        try{
            a.signal();
        }finally{
            as.unlock();
        }

    }
}

@AllArgsConstructor
class AwaitSignal extends ReentrantLock{
    /**循环次数*/
    private int loopNumber;

    /**
     * 打印
     * @param content 打印内容
     * @param current 当前要进入哪一种休息室：即condition
     * @param next 下一个要进入的休息室：即condition
     */
    public void print(String content,Condition current,Condition next){
        for (int i = 0; i < loopNumber; i++) {
            lock();
            try {
                current.await();
                System.out.print(content);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                unlock();
            }
        }
    }
}