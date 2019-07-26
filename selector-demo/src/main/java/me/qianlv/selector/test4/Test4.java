package me.qianlv.selector.test4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * isRegistered()
 *
 * @author xiaoshu
 */
public class Test4 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress("localhost", 8888));

        System.out.println("A isRegistered=" + serverSocketChannel.isRegistered());
        Selector selector = Selector.open();
        SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("B isRegistered=" + serverSocketChannel.isRegistered());
        serverSocket.close();
        serverSocketChannel.close();
    }
}
