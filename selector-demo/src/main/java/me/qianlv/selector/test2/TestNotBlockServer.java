package me.qianlv.selector.test2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 测试阻塞
 *
 * @author xiaoshu
 */
public class TestNotBlockServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        System.out.println(serverSocketChannel.isBlocking());
        serverSocketChannel.configureBlocking(false);
        System.out.println(serverSocketChannel.isBlocking());
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        System.out.println("begin " + System.currentTimeMillis());
        SocketChannel socketChannel = serverSocketChannel.accept();
        System.out.println(" end " + System.currentTimeMillis());
        socketChannel.close();
        serverSocketChannel.close();
    }
}
