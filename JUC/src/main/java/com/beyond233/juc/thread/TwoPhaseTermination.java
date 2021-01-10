package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>项目文档: 两阶段终止模式</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 16:16
 */
@Slf4j(topic = "TwoPhaseTermination")
public class TwoPhaseTermination {

    private Thread monitor;

    /**
     * 启动监控线程
     * */
    public void start() {
        Runnable r = () -> {
            while (true){
                Thread ct = Thread.currentThread();
                //如果打断标记为true：料理后事，结束
                if (ct.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                //没有被打断：每隔1秒执行监控
                try {
                    //情况1（被打断后打断标记将置为false）
                    Thread.sleep(1000);
                    //情况2 （被打断后打断标记将置为true）
                    log.debug("执行监控");
                } catch (InterruptedException e) {
                    //如果情况1（睡眠过程）中被打断：此时会清除打断标记(即仍是false)，会继续进行循环，所以需重新设置线程的打断标记
                    e.printStackTrace();
                    //重新设置线程的打断标记
                    ct.interrupt();
                }
            }
        };
        monitor = new Thread(r);
        monitor.start();
    }

    /**
     *  终止监控线程
     */
    public void stop(){
        //打断线程
        monitor.interrupt();
    }
}

@Slf4j(topic = "Test")
class TwoPhaseTerminationTest{
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination termination = new TwoPhaseTermination();
        termination.start();

        Thread.sleep(3500);
        termination.stop();
    }
}