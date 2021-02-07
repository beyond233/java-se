package com.beyond233.juc.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 描述: ForkJoin
 * 1.创建ForkJoinPool
 * 2.继承ForkJoinTask子类 ： ForkJoinPool.execute(ForkJoinTask task)
 * 提交给 Fork/Join 线程池的任务需要继承 RecursiveTask（有返回值）或 RecursiveAction（没有返回值）
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 11:01
 */
public class SumTask extends RecursiveTask<Long> {
    private static final long serialVersionUID = -2929127185135633743L;
    private long start = 0L;
    private long end = 10_0000_0000L;

    public SumTask() {
    }

    public SumTask(long start, long end) {
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
            SumTask task1 = new SumTask(start, mid);
            task1.fork();
            SumTask task2 = new SumTask(mid + 1, end);
            task2.fork();
            //合并结果
            sum = task1.join() + task2.join();
        }
        return sum;
    }

    public static void main(String[] args) {
        SumTask sumTask = new SumTask(1, 10000000);
        System.out.println(sumTask.compute());
    }
}
