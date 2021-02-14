package com.beyond233.java.se.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 描述: nio实现通信client
 *
 * @author beyond233
 * @since 2021/2/14 23:06
 */
public class MyClient {
    /**
     * buffer缓冲区大小
     */
    private int capacity = 1024;
    /**
     * buffer
     */
    private ByteBuffer byteBuffer;
    /**
     * server socket
     */
    private SocketChannel socketChannel;

    /**
     * 连接到对应服务端
     *
     * @param port 服务端的对应端口
     * @since 2021/2/15 0:17
     */
    public void connectServer(int port) throws IOException {
        System.out.println("====Client====\n");
        // init
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);
        byteBuffer = ByteBuffer.allocate(capacity);
        // receive
        receive();
    }

    /**
     * 从服务端接受数据
     *
     * @since 2021/2/15 0:14
     */
    private void receive() throws IOException {
        while (true) {
            byteBuffer.clear();
            // 如果没有数据可读，read方法会一直阻塞，直到读取到新的数据
            while (socketChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                System.out.print("server: ");
                while (byteBuffer.hasRemaining()) {
                    System.out.print((char) byteBuffer.get());
                }
                send("hello server".getBytes());
                byteBuffer.clear();
            }
        }
    }

    /**
     * 发送数据到server
     *
     * @param data 要发送的数据
     * @since 2021/2/15 0:15
     */
    private void send(byte[] data) throws IOException {
        byteBuffer.clear();
        byteBuffer.put(data);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    public static void main(String[] args) throws IOException {
        new MyClient().connectServer(9999);
    }
}
