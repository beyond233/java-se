package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>项目文档: interrupt()打断park线程 </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-09 16:56
 */
@Slf4j(topic = "interrupt()打断park线程")
public class Park {
    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            log.debug("park 开始");
            //调用park()方法暂停线程运行
            LockSupport.park();
            log.debug("被打断: park 结束");
            //打断后park线程的打断标记为true。isInterrupted()返回当前打断状态后并不会清除打断标记
            log.debug("打断状态：{}",Thread.currentThread().isInterrupted());

            //如果打断标记为true时park()方法失效，不会停止线程运行，所以下面的日志仍然会输出
            //interrupted()返回当前打断状态后会清除打断标记：将其置为false
            log.debug("打断状态：{}",Thread.interrupted());
            log.debug("打断状态：{}",Thread.currentThread().isInterrupted());
            LockSupport.park();
            log.debug("被打断: park 结束");
        };

        Thread t = new Thread(r);

        t.start();
        Thread.sleep(1);
        t.interrupt();


    }
}
