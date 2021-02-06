package com.beyond233.juc.util;

import java.util.concurrent.TimeUnit;

/**
 * 描述: 线程睡眠工具类
 *
 * @author beyond233
 * @since 2021/1/30 11:20
 */
public class Sleeper {

    public static void second(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void millisecond(long timeout) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
