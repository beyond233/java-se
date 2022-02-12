package com.beyond233;

import java.util.concurrent.TimeUnit;

/**
 * description: 栈上分配
 * <p>
 * 设置VM参数： -Xmx1G  -Xms1G -XX:+PrintGCDetails -XX:+DoEscapeAnalysis
 * 通过设置VM参数为-XX:+DoEscapeAnalysis 或 -XX:-DoEscapeAnalysis 来选择是否开启逃逸分析，并观察二者耗费时间和GC情况的区别
 *
 * @author beyond233
 * @since 2021/8/16 23:12
 */
public class StackAlloc {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            alloc();
        }

        System.out.println("耗费时间:  " + (System.currentTimeMillis() - start) + "ms");

        // 为了便于使用jvisualVM观察堆内存中对象个数，这里sleep1000秒
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void alloc() {
        StackAlloc stackAlloc = new StackAlloc();
    }
}
