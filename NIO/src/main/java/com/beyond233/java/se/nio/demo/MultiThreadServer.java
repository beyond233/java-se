package com.beyond233.java.se.nio.demo;

import com.beyond233.java.se.nio.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 多线程server: 每个线程关联一个selector，充分发挥多核CPU的性能，
 * 其中，boss线程负责建立连接，worker线程负责socketChannel上的读写事件处理
 *
 * @author beyond233
 * @since 2021/5/4 14:51
 */
@Slf4j
public class MultiThreadServer {

    public static void main(String[] args) throws IOException {
        // 1. boss: build connect
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));


        // 2. worker: handle read & write
        // create worker thread and init it
        Worker[] workers = new Worker[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }
        AtomicInteger index = new AtomicInteger();

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey scKey = iterator.next();
                iterator.remove();
                if (scKey.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    System.out.println(("[connected] IP=" + sc.getRemoteAddress()));
                    // 3. register socketChannel to worker's selector
                    System.out.println("[before register] IP=" + sc.getRemoteAddress());
                    // robin round 轮询选择一个worker
                    workers[index.getAndIncrement() % workers.length].register(sc, SelectionKey.OP_READ);
                    System.out.println("[after register] IP=" + sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        /**
         * worker's name
         */
        private String name;
        /**
         * 是否已经启动过
         */
        private boolean isStarted = false;
        /**
         * 此队列用于在boss和worker这两个线程中传递数据（要在两个线程间传递数据并且让代码不是立刻执行而是在某个位置执行时可用此队列）
         */
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        /**
         * init thread, selector
         */
        public void register(SocketChannel socketChannel, int ops) throws IOException {
            // 初始化代码只执行一次
            if (!isStarted) {
                thread = new Thread(this, name);
                selector = Selector.open();
                thread.start();
                isStarted = true;
            }
            // 向队列中添加了任务，但这个任务并没有立刻执行 (在boss线程中执行)
            queue.add(() -> {
                try {
                    socketChannel.register(selector, ops);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            // 唤醒阻塞的select()方法
            selector.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // select()： 没有事件发生的话就会阻塞线程
                    selector.select();
                    Runnable registerTask = queue.poll();
                    if (registerTask != null) {
                        registerTask.run();
                    }

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(32);
                            SocketChannel channel = (SocketChannel) key.channel();
                            channel.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
