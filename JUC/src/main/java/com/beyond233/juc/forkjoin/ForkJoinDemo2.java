package com.beyond233.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 11:19
 */
public class ForkJoinDemo2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test2(); //600        561

    }

    /**
     * 普通for循环实现
     * */
    public static void test1(){
        long sum = 0;
        long start = System.currentTimeMillis();
        for (long i = 0; i <= 10_0000_0000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum= "+sum+" 耗时："+(end-start));
    }

    /**
     * ForkJoin
     * */
    public static void test2() throws ExecutionException, InterruptedException {

        long sum;
        long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinDemo task = new ForkJoinDemo(0L,10_0000_0000L);
        //提交任务
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);
        sum = submit.get();

        long end = System.currentTimeMillis();
        System.out.println("sum= "+sum+" 耗时："+(end-start));
    }

    /**
     * stream并行流
     * */
    public static void test3(){
        long sum;
        long start = System.currentTimeMillis();

        sum = LongStream.rangeClosed(0L, 10_0000_0000L).parallel().reduce(0, Long::sum);

        long end = System.currentTimeMillis();
        System.out.println("sum= "+sum+" 耗时："+(end-start));
    }
}
