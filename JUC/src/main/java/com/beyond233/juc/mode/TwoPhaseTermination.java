package com.beyond233.juc.mode;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 描述:使用volatile重构两阶段中止模式
 *
 * @author beyond233
 * @since 2021/1/28 21:58
 */
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();

        new Thread(monitor::start).start();
        new Thread(monitor::start).start();
        new Thread(monitor::start).start();

        TimeUnit.SECONDS.sleep(3);

        new Thread(monitor::stop).start();
    }
}

@Slf4j
class Monitor {

    /**
     * 监控线程
     */
    private Thread monitor;
    /**
     * 线程停止标识
     */
    private volatile boolean stop = false;

    /**
     * 线程是否启动标识
     */
    private volatile boolean starting = false;

    /**
     * 启动监控线程
     */
    public void start() {
        // 犹豫模式启动单例监控线程
        synchronized (this) {
            if (starting) {
                log.info("监控已经启动了！！！！！！！！！！！！");
                return;
            }
            starting = true;
        }
        monitor = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.info("停止监控............");
                    break;
                }

                log.info("监控启动，开始监控,每隔一秒进行一次监控....");

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "监控器");
        monitor.start();
    }

    /**
     * 终止线程的运行
     */
    public void stop() {
        stop = true;
        // interrupt可以直接打断睡眠，使得线程可以立即停止下来
        monitor.interrupt();
    }

}
