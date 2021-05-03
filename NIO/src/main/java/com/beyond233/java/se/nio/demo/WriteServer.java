package com.beyond233.java.se.nio.demo;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * description: 服务器向客户端发送大量数据时由于发送带宽有效，一次写不完数据，写的时候buffer会被写满，此时会不断
 * 循环尝试再次发送，直到将所有数据发送到客户端。此时的解决方法是采用写事件来处理
 *
 * @author beyond233
 * @since 2021/5/4 0:15
 */
public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        int count = 0;
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                // 处理key关联的事件
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);

                    // 向客户端发送大量数据
                    StringBuilder message = new StringBuilder();
                    for (int i = 0; i < 60000000; i++) {
                        message.append("hi!");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(message.toString());
                    // 循环发送多次，效率低
//                    while (buffer.hasRemaining()) {
//                        // write()返回实际写入socketChannel的字节数
//                        int n = sc.write(buffer);
//                        System.out.println(n);
//                    }

                    // 先发送一次
                    int n = sc.write(buffer);
                    System.out.println(n);
                    count += n;
                    // 判断是否有剩余内容
                    if (buffer.hasRemaining()) {
                        // 关注可写事件
                        scKey.interestOps(scKey.interestOps() + SelectionKey.OP_WRITE);
                        // 把buffer未写完的数据挂到key上
                        scKey.attach(buffer);
                    }
                } else if (key.isReadable()) {
                    // todo
                } else if (key.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int n = sc.write(buffer);
                    System.out.println(n);
                    count += n;

                    // buffer数据写完后从key上解除，节省内存并去除对写事件的关注
                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                        System.out.println("count = " + count);
                    }
                }

            }

        }
    }

}
