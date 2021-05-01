package com.beyond233.java.se.nio.buffer;

import com.beyond233.java.se.nio.util.ByteBufferUtil;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * 描述: 通道是一个蕴含煤炭的矿藏，而缓冲区是派送到矿藏运载煤炭的卡车。
 *
 * @author beyond233
 * @since 2020/12/6 0:28
 */
public class ByteBuffer$ {

    public void test() {
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
                buffer.rewind();
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

    @Test
    public void test3() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);
        ByteBufferUtil.debugAll(buffer);

        buffer.put(new byte[]{0x62, 0x63, 0x64});
        ByteBufferUtil.debugAll(buffer);

        buffer.flip();
        System.out.println(buffer.get());
        ByteBufferUtil.debugAll(buffer);

        // compact 方法，是把未读完的部分向前压缩，然后切换至写模式
        buffer.compact();
        ByteBufferUtil.debugAll(buffer);
    }

    @Test
    public void test4() {
        // Java堆内存，读写效率低，收到GC的影响
        System.out.println(ByteBuffer.allocate(10).getClass());
        // 直接内存，读写效率高（少一次拷贝），不会受到GC的影响， 分配的效率低
        System.out.println(ByteBuffer.allocateDirect(10).getClass());
    }

    @Test
    public void test5() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();

        buffer.get(new byte[4]);
        ByteBufferUtil.debugAll(buffer);
        // position置为0，重置mark标记
        buffer.rewind();
        ByteBufferUtil.debugAll(buffer);

        System.out.println(buffer.position());
        // get(int i)不会移动position，而get()会
        buffer.get(1);
        System.out.println(buffer.position());
    }

    /**
     * string和bytebuffer的转换
     */
    @Test
    public void test6() {
        // string  -> bytebuffer
        // 1.转换后还是为写模式，position为5，limit为10
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        ByteBufferUtil.debugAll(buffer);

        // 2. 转为buffer后会自动切换为读模式，position为0，limit为5
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer1);

        // 3.   转为buffer后会自动切换为读模式，position为0，limit为5
        ByteBuffer buffer2 = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(buffer2);

        //  bytebuffer -> string
        System.out.println(StandardCharsets.UTF_8.decode(buffer1));

        buffer.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer));
    }

    /**
     * 分散读取
     */
    @Test
    public void scatterRead() throws IOException {
        FileChannel channel = new RandomAccessFile("src/main/resources/world.txt", "rw").getChannel();
        ByteBuffer buffer1 = ByteBuffer.allocate(5);
        ByteBuffer buffer2 = ByteBuffer.allocate(1);
        ByteBuffer buffer3 = ByteBuffer.allocate(5);
        channel.read(new ByteBuffer[]{buffer1, buffer2, buffer3});
        buffer1.flip();
        buffer2.flip();
        buffer3.flip();
        System.out.println(StandardCharsets.UTF_8.decode(buffer1));
        System.out.println(StandardCharsets.UTF_8.decode(buffer2));
        System.out.println(StandardCharsets.UTF_8.decode(buffer3));


    }

    /**
     * 聚集写入
     */
    @Test
    public void s() throws IOException {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode(" ");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("world");

        FileChannel channel = new FileOutputStream("src/main/resources/world" + Math.random() * 10 + ".txt").getChannel();
        channel.write(new ByteBuffer[]{b1, b2, b3});
    }

    /**
     * 黏包和半包
     */
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("hello ,i'm jack.\n ho".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    public static void split(ByteBuffer source) {
        source.flip();
        ByteBufferUtil.debugAll(source);
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                ByteBuffer target = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }


}
