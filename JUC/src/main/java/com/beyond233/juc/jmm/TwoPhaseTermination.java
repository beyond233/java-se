package com.beyond233.juc.jmm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <p>项目文档: 使用volatile重构之前的两阶段终止模式</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-18 22:26
 */
@Slf4j(topic = "main")
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        Monitor monitor = new Monitor();
        monitor.start();
        Thread.sleep(3500);
        new Thread(monitor::stop).start();
    }
}

@Slf4j(topic = "monitor")
class Monitor{
    /**监控线程*/
    private Thread monitorThread;
    /**停止标记*/
    private   boolean stop=false;

    /**启动监控*/
    public void start(){
        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    log.debug("执行监控");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        monitorThread.start();
    }

    public void stop(){
        stop = true;
//        monitorThread.interrupt();
    }

}
