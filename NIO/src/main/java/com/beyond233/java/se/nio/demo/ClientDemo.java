package com.beyond233.java.se.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * nio 客户端模拟
 *
 * @author beyond233
 * @version 1.0
 * @since 2021-01-02 18:30
 */
public class ClientDemo {

    public static void main(String[] args) throws IOException {
        // 获取一个socketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 设置为非阻塞
        socketChannel.configureBlocking(false);
        // 提供要连接的服务端的ip和端口信息
        InetSocketAddress address = new InetSocketAddress("127.0.01", 6666);

        // 连接服务端
        if (!socketChannel.connect(address)) {

            // 连接服务端是需要时间的，在还未连接完成的这段时间内客户端不会阻塞，可以去做别的事情
            // finishConnect()返回true表示客户端已完成对服务端的连接工作
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端正在连接服务端.....");
            }

            // 成功连接到服务端后向其发送数据
            String message = "hello server";
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(buffer);

        }


    }

}
