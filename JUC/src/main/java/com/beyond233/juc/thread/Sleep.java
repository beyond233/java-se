package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 10:57
 */
@Slf4j(topic = "sleep方法")
public class Sleep {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("进入睡眠前："+this.getState().toString());
                //线程启动后为RUNNABLE状态，sleep方法启动后会转变为TIMED_WAITING状态
                try {
                    Thread.sleep(2000);
                    log.debug("睡眠结束后："+this.getState().toString());
                } catch (InterruptedException e) {
                    log.debug("睡眠被打断："+this.getState().toString());
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        //让主线程睡眠1s后，t1线程才肯定进入睡眠，才能捕捉他的状态
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //线程启动后为RUNNABLE状态，sleep方法启动后会转变为TIMED_WAITING状态
        log.debug("t1睡眠中："+t1.getState().toString());
        t1.interrupt();
        log.debug("t1被打断："+t1.getState().toString());

    }
}
