package com.beyond233;

/**
 * description: 设置VM参数为：-Xms60m  -Xmx60m -XX:NewRatio=2 -XX:SurvivorRatio=8  -XX:+PrintGCDetails
 * 测试: 大对象创建后直接进入老年代，即按照上面的JVM配置，eden区16m,survivor区2m,old区40m，所以20m的数组不能放进eden，直接放进old区中
 *
 * @author beyond233
 * @since 2021/8/10 23:11
 */
public class YoungOldAreaTest {
    public static void main(String[] args) {
        // new一个20m大小的数组，
        byte[] bytes = new byte[1024 * 1024 * 20];
    }
}
