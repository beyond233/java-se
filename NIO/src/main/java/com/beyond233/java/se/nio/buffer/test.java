package com.beyond233.java.se.nio.buffer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * description:
 *
 * @author beyond233
 * @since 2021/5/2 1:09
 */
public class test {
    public static void main(String[] args) throws IOException {
        ByteBuffer b1 = ByteBuffer.allocate(5);
        ByteBuffer b2 = ByteBuffer.allocate(1);
        ByteBuffer b3 = ByteBuffer.allocate(5);
        System.out.println(b1.position());
        System.out.println(b1.limit());
        b1.flip();
        b2.flip();
        b3.flip();

        FileChannel channel = new FileOutputStream("world" + Math.random() + ".txt").getChannel();
        channel.write(new ByteBuffer[]{b1, b2, b3});
    }
}
