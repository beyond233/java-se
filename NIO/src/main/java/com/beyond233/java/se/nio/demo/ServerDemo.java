package com.beyond233.java.se.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>项目文档:选择器学习 </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2021-01-02 16:57
 */
public class ServerDemo {

    public static void main(String[] args) throws IOException {
        // 1.创建serverSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个selector对象
        Selector selector = Selector.open();
        // 绑定端口，在服务端开始监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞状态
        serverSocketChannel.configureBlocking(false);
        // 将serverSocketChannel注册到selector，关心事件为：accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 2.循环等待客户端进行连接
        while (true) {
            // 等待1秒钟，若无事件发生则返回
            if (selector.select(1000) == 0) {
                System.out.println("无客户端连接");
                continue;
            }

            // 3.若有客户端进项连接，则获取关注事件的集合selectionKeys(可通过其获取channel)
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 4.使用迭代器遍历selectionKeys,根据key对应通道发生的事件进行响应处理
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                // 根据key对应通道发生的事件进行响应处理
                // 4.1 发生accept事件表示有新的客户端进行连接
                if (key.isAcceptable()) {
                    // 获取该客户端的socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // socketChannel注册到selector
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 4.2 发生read事件
                if (key.isReadable()) {
                    // 通过key获取对应channel
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 获取关联buffer
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    // 读取数据到buffer
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端数据为： " + new String(byteBuffer.array()));
                }

                // 移除selectionKey，防止重复操作
                iterator.remove();
            }
        }
    }
}

