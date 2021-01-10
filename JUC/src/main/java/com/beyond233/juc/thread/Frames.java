package com.beyond233.juc.thread;

/**
 * <p>项目文档: 栈与栈帧</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-07 16:34
 */
public class Frames {
    public static void main(String[] args) {
        method1(1);
    }

    public static void method1(int x){
        int y = x + 1;
        System.out.println(y);
        Object o = method2();
        System.out.println(o);
    }

    public static Object method2(){
        return new Object();
    }
}
