package com.beyond233.java.se.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * description:
 *
 * @author beyond233
 * @since 2021/5/2 21:48
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8088));

        ArrayList<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // accept为阻塞方法，线程会停止运行
            SocketChannel socketChannel = serverSocketChannel.accept();
            channels.add(socketChannel);

            for (SocketChannel channel : channels) {
                socketChannel.read(buffer);
                // read也是阻塞方法,在read时就不能accept其他客户端的连接，反之accept时也不能read，阻塞时当前线程会停止运行
                buffer.flip();
                System.out.println(buffer.asCharBuffer());
                buffer.clear();
            }
        }

    }
}
