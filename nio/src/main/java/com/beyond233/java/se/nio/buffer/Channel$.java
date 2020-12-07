package com.beyond233.java.se.nio.buffer;

import org.junit.Test;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.io.*;
import java.lang.annotation.IncompleteAnnotationException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

/**
 * 一、描述: 通道
 *  channel在nio中负责缓冲区中数据的传输。channel本身不存储数据，因此需要配置缓冲区进行数据的传输
 *
 *  二、channel为接口，主要实现类有：
 *          FileChannel
 *          SocketChannel
 *          ServerSocketChannel
 *          DatagramChannel
 *  三、获取Channel
 *    java针对支持channel的类提供了getChannel()方法
 *
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
            if (outChannel!=null) {
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
     *  分散读取(scattering reads)：将一个通道中的数据分散到多个缓冲区中
     *  聚集写入(gathering writes)：见多个缓冲区中的数据聚集到一个通道中
     *
     * @author beyond233
     * @since 2020/12/6 22:18
     */
    @Test
    public void scatteringAndGathering() throws IOException {
        RandomAccessFile inFile = new RandomAccessFile("src/main/resources/hello.txt", "rw");
        // 1.
        FileChannel inChannel = inFile.getChannel();
        // 2.
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

    @Test
    public void charset() {
        SortedMap<String, Charset> map = Charset.availableCharsets();

        for (Map.Entry<String, Charset> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        Charset gbk = Charset.forName("GBK");
        CharsetEncoder encoder = gbk.newEncoder();
        CharsetDecoder decoder = gbk.newDecoder();

    }


}
