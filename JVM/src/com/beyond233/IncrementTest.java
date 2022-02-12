package com.beyond233;

/**
 * description:从字节码角度分析i++与++i的区别
 *
 * @author beyond233
 * @since 2021/8/2 11:30
 */
public class IncrementTest {
    public static void main(String[] args) {
//        int i = 0;
//        i = i++;
//        i = ++i;
//        i = i++ + ++i;
//        System.out.println(i);
        //        inc();
    }

    public static void inc() {
        int i = 0;
//        i = i++;
//        i = ++i;
        i = i++ + ++i;
        inc();
    }
}
