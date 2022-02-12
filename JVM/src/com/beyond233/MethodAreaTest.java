package com.beyond233;

import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author beyond233
 * @since 2021/8/23 22:09
 */
public class MethodAreaTest {
    public static void main(String[] args) {
        System.out.println("start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
