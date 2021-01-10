package com.beyond233.juc.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <p>项目文档:创建线程的多种方式 </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-06-05 23:11
 */
@Slf4j(topic = "创建线程")
public class CreateThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //方式1
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                yield();
                log.info("t1 running");
            }
        };
        t1.start();

        //方式2：实现Runnable接口，复写run()方法。将其作为参数传入Thread构造器中
        Runnable r = ()->log.info("t2 running");
        Thread t2 = new Thread(r,"t2");
        //上面两步可以合为一步：
//        Thread t2 = new Thread(()->log.info("t2 running"),"t2");
        t2.start();

        //方式3 FutureTask可以接收方法执行结果，将其传递给其他线程
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("t3 running");
                Thread.sleep(3000);
                return 1;
            }
        });
        Thread t3 = new Thread(futureTask,"t3");
        t3.start();
        Integer result = futureTask.get();
        log.info("futureTask执行结果：" + result);

    }
}
