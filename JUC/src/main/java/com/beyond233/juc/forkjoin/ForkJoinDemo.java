package com.beyond233.juc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * <p>项目文档: ForkJoin
 * 1.创建ForkJoinPool
 * 2.继承ForkJoinTask子类 ： ForkJoinPool.execute(ForkJoinTask task)
 * </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 11:01
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private static final long serialVersionUID = -2929127185135633743L;
    private long start = 0L;
    private long end = 10_0000_0000L;

    public ForkJoinDemo() {
    }

    public ForkJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    /**
     * 计算方法
     */
    @Override
    protected Long compute() {
        long sum = 0;
        //计算量小于10000就使用普通for循环计算方式
        if ((end - start) <= 1_0000L) {
            for (long i = 0; i <= end; i++) {
                sum += i;
            }
        } else {
            //当计算量大于10000时采用ForkJoin拆分计算
            //计算中间值
            long mid = (end + start) / 2;
            //拆分计算任务并将其压入线程队列
            ForkJoinDemo task1 = new ForkJoinDemo(start, mid);
            task1.fork();
            ForkJoinDemo task2 = new ForkJoinDemo(mid + 1, end);
            task2.fork();
            //合并结果
            sum = task1.join() + task2.join();
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinDemo demo = new ForkJoinDemo(1, 10000000);
        System.out.println(demo.compute());
    }
}
