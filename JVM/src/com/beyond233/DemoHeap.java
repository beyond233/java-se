package com.beyond233;

/**
 * description: 设置Vm参数：-Xms600m -Xmx600m -XX:+PrintGCDetails
 * 上面vm参数设置堆区大小为600m,而实际内存大小却为575m，这是因为survivor0区和survivor1区只能使用其中一个
 *
 * @author beyond233
 * @since 2021/8/2 17:15
 */
public class DemoHeap {
    public static void main(String[] args) throws InterruptedException {
        Object o = new Object();
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024 + "m");
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024 + "m");
    }
}
