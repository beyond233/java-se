package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>项目文档: 守护线程</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 17:30
 */
@Slf4j(topic = "守护线程")
public class DaemonThread {
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true){
                    if (Thread.currentThread().isInterrupted()) {
                        break;
                    }
                }
                log.debug("t1线程结束");
            }
        };
        //设置当前线程为守护线程：当非守护线程结束全部结束后，守护线程会被强制结束
        t1.setDaemon(true);
        t1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("主线程结束");
    }
}
