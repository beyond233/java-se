package com.beyond233.juc.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.concurrent.*;

/**
 * learn threadPool
 *
 * @author beyond233
 * @date 2021/2/7 13:59
 */
@Slf4j(topic = "-")
public class ThreadPoolLearn {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1,
                new NamedThreadFactory("schedule", 3, false), (task, queue) -> {
            // 拒绝策略
            task.run();
        });

        // 1.scheduleAtFixedRate: 以指定延时、间隔去执行任务
        executor.scheduleAtFixedRate(() -> log.info("A"), 1, 2, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(() -> log.info("B"), 2, 2, TimeUnit.SECONDS);

        // 2.submit可以获取任务的返回值
        Future<Integer> future = executor.submit(() -> 1 + 1);
        log.info(String.valueOf(future.get()));

        // 3.schedule相当于带延时的submit
        ScheduledFuture<?> scheduledFuture = executor.schedule(() -> LocalDate.now().plusDays(1), 1, TimeUnit.SECONDS);
        log.info("result：" + scheduledFuture.get());

        // 4.execute
        executor.execute(() -> log.info("execute........"));
    }

}
