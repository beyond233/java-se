package com.beyond233.java.se.nio.demo;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 描述: nio实现通信server
 *
 * @author beyond233
 * @since 2021/2/14 23:06
 */
@Slf4j
public class MyServer {
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
    private ServerSocketChannel serverSocketChannel;
    /**
     * selector选择器
     */
    private Selector selector;
    /**
     * 远程连接的客户端的数量
     */
    private int remoteClientNum = 0;

    public MyServer(int port) {
        try {
            // 在构造函数中初始化channel监听
            init(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * 初始化channel、buffer、selector
     *
     * @param port 端口
     * @since 2021/2/14 23:07
     */
    public void init(int port) throws IOException {
        System.out.println("====Server====\n");
        // 打开channel
        serverSocketChannel = ServerSocketChannel.open();
        // 设置channel为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(port));
        log.info("listen on port: {}", port);
        // 创建选择器
        selector = Selector.open();
        // 向选择器注册channel
        registerChannel(selector, serverSocketChannel, SelectionKey.OP_ACCEPT);
        // 分配缓冲区大小
        byteBuffer = ByteBuffer.allocate(capacity);
    }

    /**
     * 监听channel上的数据变化
     *
     * @since 2021/2/14 23:13
     */
    private void listen() throws IOException {
        while (true) {
            // select()方法没有事件发生时线程阻塞，又事件线程才会恢复运行
            // select()方法在事件未处理时不会阻塞，事件发生后要么处理要么取消，不能置之不理
            // n表示有多少个channel正处于就绪状态
            int n = selector.select();
            if (n == 0) {
                continue;
            }
            // 1 获取selectionKey，一个selectionKey对应一个channel
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 2 遍历selectionKey集合，根据其状态进行对应处理
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 2.1 如果selectionKey处于连接就绪状态，则开始接受客户端的连接
                if (key.isAcceptable()) {
                    // 获取channel
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // channel接受连接（处理accept事件）
                    SocketChannel socketChannel = server.accept();
                    // channel注册
                    registerChannel(selector, socketChannel, SelectionKey.OP_READ);
                    // 远程客户端连接数加一
                    log.info("online client number: {}", ++remoteClientNum);
                    // say hello to client
                    write(socketChannel, "hello client".getBytes());
                }
                // 2.2 如果selectionKey处于读就绪状态，则读取channel数据进行业务处理
                if (key.isReadable()) {
                    read(key);
                }
                // 移除selectionKey，防止重复处理key上的事件从而导致异常，
                // 比如某个key上的accept事件已处理过了，下次再处理这个key时，并没有accept事件发生了，此时调用方法可能发生NPE
                iterator.remove();
            }
        }
    }

    /**
     * 读取数据并进行处理
     *
     * @param key selectionKey
     * @since 2021/2/14 23:33
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        byteBuffer.clear();
        // 从channel读取数据到buffer中
        while ((count = socketChannel.read(byteBuffer)) > 0) {
            // buffer切换写模式为读模式
            byteBuffer.flip();
            System.out.print("client: ");
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
        }
        // 如果客户端正常断开，read()返回值是-1
        if (count < 0) {
            socketChannel.close();
        }
    }

    /**
     * 写数据到buffer中
     *
     * @param channel channel
     * @param data    数据
     * @since 2021/2/14 23:34
     */
    private void write(SocketChannel channel, byte[] data) throws IOException {
        byteBuffer.clear();
        byteBuffer.put(data);
        // flip
        byteBuffer.flip();
        // 将buffer中数据写入channel中
        channel.write(byteBuffer);
    }

    /**
     * 向selector注册channel
     *
     * @param selector  选择器
     * @param channel   通道
     * @param Operation 操作事件
     * @since 2021/2/14 23:50
     */
    private void registerChannel(Selector selector, SelectableChannel channel, int Operation) throws IOException {
        if (channel == null) {
            return;
        }
        //设置channel为非阻塞模式
        if (channel.isBlocking()) {
            channel.configureBlocking(false);
        }
        channel.register(selector, Operation);
    }

    public static void main(String[] args) throws IOException {
        new MyServer(9999).listen();
    }
}
