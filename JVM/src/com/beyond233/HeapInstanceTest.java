package com.beyond233;


import java.util.ArrayList;
import java.util.Random;

/**
 * description: 设置VM参数为：-Xms600m  -Xmx600m -XX:NewRatio=2 -XX:SurvivorRatio=6
 * 设置好VM参数后启动程序，通过jvisualVM观察堆中eden、survivor、old区的大小变化
 *
 * @author beyond233
 * @since 2021/8/4 18:00
 */
public class HeapInstanceTest {
    // 至少200kb
    byte[] buffer = new byte[new Random().nextInt(1024 * 200)];

    public static void main(String[] args) {
        ArrayList<HeapInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeapInstanceTest());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
