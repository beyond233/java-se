package com.beyond233.juc.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 描述:使用unsafe实现原子integer
 *
 * @author beyond233
 * @since 2021/1/31 23:41
 */
class MyAtomicInteger {
    private volatile int value;
    private static long valueOffset;
    private static Unsafe UNSAFE = null;

    static {
        try {
            // 初始化unsafe
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);

            // 获取value的偏移地址
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    MyAtomicInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void decrease(int amount) {
        while (true) {
            int prev = getValue();
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        MyAtomicInteger atomicInteger = new MyAtomicInteger(1000);

        ArrayList<Thread> list = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            list.add(new Thread(() -> atomicInteger.decrease(10)));
        }
        list.forEach(Thread::start);
        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(atomicInteger.getValue());
    }

}
