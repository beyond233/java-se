package com.beyond233.juc.cas;

import com.beyond233.juc.entity.User;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 描述: Unsafe类学习
 *
 * @author beyond233
 * @since 2021/1/31 23:04
 */
public class UnsafeLearn {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 1.反射获取unsafe类对象
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        System.out.println(theUnsafe);
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

        // 2.获取字段的偏移地址
        long nameOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("name"));
        long ageOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("age"));
        // 3.使用unsafe的cas方法修改字段的值
        User user = User.getInstance();
        System.out.println(user);

        unsafe.compareAndSwapObject(user, nameOffset, null, "beyond");
        unsafe.compareAndSwapInt(user, ageOffset, 0, 18);

        System.out.println(user);

    }
}


