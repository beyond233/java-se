package com.beyond233.java.se.nio.buffer;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * <p>项目文档: 群聊服务端</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2021-01-02 23:38
 */
@Slf4j
public class GroupChatServer {

    /**
     * selector
     */
    private Selector selector;
    /**
     * serverSocketChannel
     */
    private ServerSocketChannel listenChannel;
    /**
     * port
     */
    private static final int PORT = 6666;

    /**
     * init selector、listenChannel、port
     *
     * @since 2021/1/2 23:41
     */
    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // mode=non-blocking
            listenChannel.configureBlocking(false);
            // channel register to selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("groupChatServer init failed");
        }


    }
}
