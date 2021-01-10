package com.beyond233.juc.lock;


import lombok.AllArgsConstructor;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>项目文档: wait,notify实现循环打印</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-18 16:46
 */
public class LoopPrintDemo2 {
    static Thread t1;
    static Thread t2;
    static Thread t3;


    public static void main(String[] args) throws InterruptedException {
        //wait,notify实现
        System.out.println("waitNotify: ");
        WaitNotify wn = new WaitNotify(1,5);

        new Thread(()->wn.print("a",1,2)).start();
        new Thread(()->wn.print("b",2,3)).start();
        new Thread(()->wn.print("c",3,1)).start();

        Thread.sleep(2000);
        //park,unPark实现
        System.out.println("\n"+"parkUnPark:");
        ParkUnPark pu = new ParkUnPark(5);
        t1 = new Thread(() -> pu.print("a",t2));
        t2 = new Thread(() -> pu.print("b",t3));
        t3 = new Thread(() -> pu.print("c",t1));

        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }
}

@AllArgsConstructor
class WaitNotify{
    /**等待标记*/
    private int flag;
    /**循环次数*/
    private int loopCount;

    /**
     * 循环打印
     * @param content 打印内容
     * @param waitFlag 等待标记
     * @param nextFlag 下一个打印标记
     */
    public void print(String content,int waitFlag,int nextFlag) {
        for (int i = 0; i < loopCount; i++) {
            synchronized (this){
                while (flag != waitFlag) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(content);
                flag = nextFlag;
                notifyAll();
            }
        }
    }
}

@AllArgsConstructor
class ParkUnPark{

    /**循环次数*/
    private int loopNumber;

    public void print(String content, Thread next){
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(content);
            LockSupport.unpark(next);
        }
    }
}