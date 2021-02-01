package com.beyond233.juc.util;

import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @author beyond233
 * @since 2021/1/30 11:20
 */
public class Sleeper {
    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mills(int time) {
        try {
            Thread.sleep(time * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
