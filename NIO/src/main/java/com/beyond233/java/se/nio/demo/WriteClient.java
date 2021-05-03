package com.beyond233.java.se.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * description:
 *
 * @author beyond233
 * @since 2021/5/4 0:05
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
//        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress(8080));

        // 接收服务端的数据
        int count = 0;
        while (true) {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 10);
            count += sc.read(buffer);
            System.out.println(count);
            buffer.clear();
        }

    }
}
