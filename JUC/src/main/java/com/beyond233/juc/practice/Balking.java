package com.beyond233.juc.practice;

import lombok.extern.slf4j.Slf4j;

/**
 * balking暂缓模式 ：用在一个线程发现另一个线程或本线程已经做了某一件相同的事，那么本线程就无需再做
 * 了，直接结束返回
 *
 * @author beyond233
 * @date 2021/1/28 10:57
 */
@Slf4j
public class Balking {
    private volatile boolean flag = false;

    public void start() {
        // 1.加锁暂缓业务代码的执行
        log.info("尝试执行业务代码");
        synchronized (this) {
            if (flag) {
                return;
            }
            flag = true;
        }

        // 2.真正开始执行业务代码
        log.info("执行业务代码");
    }
}
