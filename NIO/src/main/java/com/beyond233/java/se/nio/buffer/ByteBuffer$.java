package com.beyond233.java.se.nio.buffer;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * 描述: 通道是一个蕴含煤炭的矿藏，而缓冲区是派送到矿藏运载煤炭的卡车。
 *
 * @author beyond233
 * @since 2020/12/6 0:28
 */
public class ByteBuffer$ {

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

        // 3.flip()切换为读取数据模式
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
        // mark()标记位置
        buffer.mark();
        System.out.println("position: --> mark " + buffer.position());

        buffer.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println("position: -->second get " + buffer.position());

        // reset()回退到mark的位置
        buffer.reset();
        buffer.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
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

    @Test
    public void test1() throws IOException {
        FileChannel channel = new FileOutputStream("src/main/resources/hello.txt").getChannel();
        // 删除hello.txt中原有内容，然后使用wrap()向缓冲区中写入新数据（因为指针从0开始）
        channel.write(ByteBuffer.wrap("可".getBytes()));
        // 移动指针到最末尾，这样写入的数据将会添加到原有内容的后面而不是覆盖掉之前的全部数据
        channel.position(channel.size());
        channel.write(ByteBuffer.wrap("真的不错呢".getBytes()));

        channel.close();
    }

    @Test
    public void getBasicData() {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        // char
//        buffer.asIntBuffer().put(4444);
//        buffer.asCharBuffer().put("beyond");
//        char c;
//        while ((c = buffer.getChar()) != 0) {
//            System.out.print(c);
//        }

        // int
        buffer.asIntBuffer().put(new int[]{1, 2, 3});
//        intBuffer.put(4);
        buffer.asIntBuffer().put(444444444);
        IntBuffer intBuffer = buffer.asIntBuffer();

        int i;
        while ((i = intBuffer.get()) != 0) {
            System.out.println(i);
        }
        System.out.println("-----------------");
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());
    }

    @Test
    public void test2() throws IOException {
        RandomAccessFile file = new RandomAccessFile("src/main/resources/data.txt", "rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ByteBuffer b1 = null;
        ByteBuffer b2 = null;
        do {
            // 向buffer写入
            int len = channel.read(buffer);

            System.out.println("len = " + len);
            if (len == -1) {
                break;
            }

            // 切换为读模式
            buffer.flip();
            while (buffer.hasRemaining()) {
                char c = (char) buffer.get();
                System.out.println(c);
//                buffer.rewind();
                if (c == '3') {
                    buffer.mark();
                    b1 = buffer;
                }

                if (c == 'b') {
                    buffer.mark();
                    b2 = buffer;
                }
            }
            // reset()前必须要mark，原因看reset源码就懂了
            buffer.reset();
            System.out.println("xxxxxxxxxxxx:  " + (char) buffer.get());
            // 切换为写模式
            buffer.clear();
        } while (true);

        System.out.println(b1 == b2);
    }


}
