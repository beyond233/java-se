package com.beyond233.java.se.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * description: client for test
 *
 * @author beyond233
 * @since 2021/5/4 15:29
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(8080));
        sc.write(Charset.defaultCharset().encode("hello server"));
        System.in.read();
    }
}
