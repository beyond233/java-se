package com.beyond233.java.se.nio.buffer;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

/**
 * 一、描述: 通道
 * channel在nio中负责缓冲区中数据的传输。channel本身不存储数据，因此需要配置缓冲区进行数据的传输
 * <p>
 * 二、channel为接口，主要实现类有：
 * FileChannel
 * SocketChannel
 * ServerSocketChannel
 * DatagramChannel
 * 三、获取Channel
 * java针对支持channel的类提供了getChannel()方法
 *
 * @author beyond233
 * @since 2020/12/6 21:56
 */
public class Channel$ {

    /**
     * 使用nio非直接缓冲区实现文件复制
     *
     * @author beyond233
     * @since 2020/12/6 22:18
     */
    @Test
    public void copy() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("src/main/resources/hello.txt");
        FileOutputStream fos = new FileOutputStream("src/main/resources/hello-copy.txt");

        // 获取channel
        FileChannel inChannel;
        FileChannel outChannel;
        inChannel = fis.getChannel();
        outChannel = fos.getChannel();
        // 分配指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(100);
        // 将channel中数据存入buffer中：先读后写
        try {
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                // buffer数据写入channel中
                outChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 使用nio直接缓冲区实现文件复制
     *
     * @author beyond233
     * @since 2020/12/6 22:18
     */
    @Test
    public void copy2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("src/main/resources/hello.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("src/main/resources/hello-copy2.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
        // 将输入channel中数据写入输出channel中
        inChannel.transferTo(0, inChannel.size(), outChannel);
//        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /**
     * 分散读入和聚集写出
     * 分散读取(scattering reads)：将一个通道中的数据分散到多个缓冲区中
     * 聚集写入(gathering writes)：见多个缓冲区中的数据聚集到一个通道中
     *
     * @author beyond233
     * @since 2020/12/6 22:18
     */
    @Test
    public void scatteringAndGathering() throws IOException {
        RandomAccessFile inFile = new RandomAccessFile("src/main/resources/hello.txt", "rw");
        // 1.获取channel
        FileChannel inChannel = inFile.getChannel();
        // 2。构造字节缓冲区
        ByteBuffer buffer1 = ByteBuffer.allocate(2);
        ByteBuffer buffer2 = ByteBuffer.allocate(3);

        // 3.分散读入
        ByteBuffer[] buffers = {buffer1, buffer2};
        inChannel.read(buffers);

        for (ByteBuffer buffer : buffers) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit()));
        }

        // 4.聚集写出
        RandomAccessFile outFile = new RandomAccessFile("src/main/resources/world.txt", "rw");
        FileChannel outChannel = outFile.getChannel();
        outChannel.write(buffers);
    }

    /**
     * 字符集
     */
    @Test
    public void charset() {
        // 获取可用的字符集
        SortedMap<String, Charset> map = Charset.availableCharsets();

        for (Map.Entry<String, Charset> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        Charset gbk = Charset.forName("GBK");
        CharsetEncoder encoder = gbk.newEncoder();
        CharsetDecoder decoder = gbk.newDecoder();

    }

    /**
     * 写入时对数据进行编码，这样读出时才会正常
     * <p>
     * 在读写buffer中数据时对其进行编解码，让其能够以正确的形式输出
     */
    @Test
    public void bufferCharsetWrite() throws Exception {
        FileChannel channel = new FileOutputStream("src/main/resources/charset.txt").getChannel();
        // 写入时对数据进行编码，这样读出时才会正常
        channel.write(ByteBuffer.wrap("编码集".getBytes(StandardCharsets.UTF_16BE)));
        channel.close();
        // 读入
        channel = new FileInputStream("src/main/resources/charset.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }

    /**
     * 在读写buffer中数据时对其进行编解码，让其能够以正确的形式输出
     */
    @Test
    public void bufferCharsetRead() throws Exception {
        FileChannel channel = new FileOutputStream("src/main/resources/charset.txt").getChannel();
        // 写出时不进行相应编码
        channel.write(ByteBuffer.wrap("编码集".getBytes()));
        channel.close();
        // 读入
        channel = new FileInputStream("src/main/resources/charset.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        channel.read(buffer);
        buffer.flip();
        // 读取buffer中数据时使用平台默认字符集对其进行解码，这样读出时才会正常
        Charset charset = Charset.forName(System.getProperty("file.encoding"));
        System.out.println(charset.decode(buffer));
//        System.out.println(StandardCharsets.UTF_8.decode(buffer));
    }

    /**
     * 两个channel之间传输数据
     */
    @Test
    public void transferTo() throws IOException {
        FileChannel from = new RandomAccessFile("src/main/resources/from.txt", "rw").getChannel();
        FileChannel to = new FileOutputStream("src/main/resources/to_" + Math.random() + ".txt").getChannel();

        // 效率高,底层会利用操作系统的零拷贝进行优化
        from.transferTo(0, from.size(), to);
        System.out.println(from.size());
        from.write(ByteBuffer.wrap("?".getBytes(StandardCharsets.UTF_8)), from.size());
        System.out.println(from.size());
    }

    @Test
    public void trans() {
        try {
            FileChannel from = new FileInputStream("C:\\Users\\BEYOND\\Downloads\\pycharm-professional-2021.1.1.exe").getChannel();
            FileChannel to = new FileOutputStream("src/main/resources/pycharm.exe").getChannel();

            long size = from.size();
            for (long left = size; left > 0; ) {
                System.out.println("position = " + (size - left) + " left = " + left);
                left -= from.transferTo(size - left, left, to);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
