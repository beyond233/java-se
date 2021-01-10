package com.beyond233.juc.threadpool;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

/**
 * <p>项目文档: 线程池</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-22 22:23
 */
public class ExecutorDemo {
    public static void main(String[] args) {
        //1.使用Executors来创建线程池,这种方式并不好

        //创建只有一个实例的连接池
//        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建一个固定数量的连接池
//        ExecutorService pool = Executors.newFixedThreadPool(10);
        //创建一个数量可伸缩的连接池
//        ExecutorService pool = Executors.newCachedThreadPool();

        // 2.使用ThreadPoolExecutor来创建线程池，可以更加明确线程池运行规则，规避资源耗尽的风险

        //2.1创建阻塞队列
        LinkedBlockingQueue queue = new LinkedBlockingQueue<>(3);
        //2.2创建线程工厂
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //2.3创建拒绝策略处理器: 总共4种。超过线程池最大承载数时由拒绝策略处理器来处理多余的连接请求
            //第一种  中止策略：超过线程池最大承载数时直接中止,即抛出异常RejectedExecutionException
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
            //第二种  回调策略：超过线程池最大承载数时由原来调用它的线程去执行它
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
            //第三种  抛弃最老策略：尝试和最早的去竞争，竞争成功则执行
        ThreadPoolExecutor.DiscardOldestPolicy discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();
            //第四种  抛弃策略：直接抛弃多的任务，不会抛出异常
        ThreadPoolExecutor.DiscardPolicy discardPolicy = new ThreadPoolExecutor.DiscardPolicy();
        //2.4创建线程池： 线程池最大承载数=线程池最大连接数+阻塞队列大小，超过最大承载则抛出异常RejectedExecutionException
        //线程池最大数量定义规则：  1.CPU密集型：按照CPU核数，几核就是几个并行线程； 2.IO密集型：判断程序中大型复杂且消耗大量IO资源的线程有多少个
        //获取CPU最大核数,以此规定线程池最大数量
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = new ThreadPoolExecutor(2,5,3, TimeUnit.SECONDS,queue,threadFactory,discardOldestPolicy);



        //3.测试线程池创建线程
        try {
            //这里线程最大承载数=5+3=8.访问数超过线程池最大连接数时才会启用核心连接以外的剩余连接
            for (int i = 0; i < 20; i++) {
                int t = i;
                //实现runnable接口方法并启动线程
                pool.execute(()->{
                    System.out.println(t+" "+Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }

    }
}
