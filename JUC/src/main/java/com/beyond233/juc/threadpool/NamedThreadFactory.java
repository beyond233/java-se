package com.beyond233.juc.threadpool;

import com.beyond233.juc.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂类： 实现线程命名
 *
 * @author beyond233
 * @date 2021/2/7 14:09
 */
public class NamedThreadFactory implements ThreadFactory {
    /**
     * 前缀
     */
    private final String prefix;
    /**
     * 前缀计数器
     */
    private final static Map<String, AtomicInteger> PREFIX_COUNTER = new ConcurrentHashMap<>();
    /**
     * 顺序计数器
     */
    private final AtomicInteger counter = new AtomicInteger(0);
    /**
     * 大小
     */
    private final int totalSize;
    /**
     * 是否标记为守护线程
     */
    private final boolean makeDaemons;

    public NamedThreadFactory(String prefix, int totalSize, boolean makeDaemons) {
        int prefixCounter = CollectionUtils.computeIfAbsent(PREFIX_COUNTER, prefix, key -> new AtomicInteger(0)).incrementAndGet();
        this.prefix = prefix + "_" + prefixCounter;
        this.totalSize = totalSize;
        this.makeDaemons = makeDaemons;
    }

    /**
     * 构造一个新线程
     */
    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + "_" + counter;
        if (totalSize > 1) {
            name += "_" + totalSize;
        }
        Thread thread = new Thread(r, name);
        thread.setDaemon(makeDaemons);
        return thread;
    }

}
