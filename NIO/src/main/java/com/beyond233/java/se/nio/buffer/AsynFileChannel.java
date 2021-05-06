package com.beyond233.java.se.nio.buffer;

import com.beyond233.java.se.nio.util.ByteBufferUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 异步文件channel
 *
 * @author beyond233
 * @date 2021/5/6 16:10
 */
@Slf4j
public class AsynFileChannel {
    public static void main(String[] args) throws IOException {
        try {
            AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("NIO/src/main/resources/data.txt"), StandardOpenOption.READ);
            ByteBuffer buffer = ByteBuffer.allocate(24);
            // 回调方法是由一个守护线程来调用的
            fileChannel.read(buffer, 0, null, new CompletionHandler<Integer, ByteBuffer>() {
                /**
                 * 文件读取完成后的回调方法
                 */
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed. result = {}", result);
                    attachment.flip();
                    ByteBufferUtil.debugAll(attachment);
                }

                /**
                 * 文件读取失败后的回调方法
                 */
                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.debug("read failed");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.debug("do other things");
        // 默认文件 AIO 使用的线程都是守护线程，所以最后要执System.in.read()以避免守护线程意外结束
        System.in.read();
    }
}
