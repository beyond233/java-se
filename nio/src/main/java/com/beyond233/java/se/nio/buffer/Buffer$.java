package com.beyond233.java.se.nio.buffer;

import javax.print.DocFlavor;
import javax.swing.*;
import java.nio.ByteBuffer;

/**
 * 描述:
 *
 * @author beyond233
 * @since 2020/12/6 0:28
 */
public class Buffer$ {

    public static void main(String[] args) {
        String str = "beyond233";

        // 1.分配一个指定大小的非直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("------------------>1.初始化");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        // 2.利用put()存入数据到缓冲区中
        buffer.put(str.getBytes());

        System.out.println("------------------>2.put数据");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        // 3.切换为读取数据模式
        buffer.flip();

        System.out.println("------------------>3.flip()切换为读取数据模式");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        // 4.利用get()读取数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst, 0, 2);
        String data = new String(dst, 0, dst.length);

        System.out.println("------------------>4.get()读取数据");
        System.out.println("data：" + data);
        System.out.println("position: -->first get" + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        // 标记位置
        buffer.mark();
        System.out.println("position: --> mark " + buffer.position());

        buffer.get(dst, 0, 2);
        System.out.println(new String(dst,0,2));
        System.out.println("position: -->second get " + buffer.position());

        // 回退到mark的位置
        buffer.reset();
        buffer.get(dst, 0, 2);
        System.out.println(new String(dst,0,2));
        System.out.println("position: -->third get " + buffer.position());

        // 7.判断缓冲区中是否还有数据
        if (buffer.hasRemaining()) {
            System.out.println("可操作的数据数量：" + buffer.remaining());
        }

        // 5.rewind() 倒带回退指针，可用来重复读取数据
        buffer.rewind();

        System.out.println("------------------>5.rewind()重复读取数据");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());

        // 6.clear() 清空缓冲区，数据依然存在，只是容量、界限、位置、标记位置等信息全部重置为初始状态了
        buffer.clear();

        System.out.println("------------------>6.clear()清空缓冲区");
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("capacity: " + buffer.capacity());



    }
}
