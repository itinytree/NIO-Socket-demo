package me.qianlv.selector.test7;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 通道注册与选择器
 *
 * @author xiaoshu
 */
public class Test7 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));

        serverSocketChannel.configureBlocking(false);
        Selector selector1 = Selector.open();
        Selector selector2 = Selector.open();

        SelectionKey selectionKey1 = serverSocketChannel.register(selector1, SelectionKey.OP_ACCEPT);
        System.out.println("selectionKey1=" + selectionKey1.hashCode());
        SelectionKey selectionKey2 = serverSocketChannel.register(selector2, SelectionKey.OP_ACCEPT);
        System.out.println("selectionKey2=" + selectionKey2.hashCode());
        SelectionKey selectionKey3 = serverSocketChannel.register(selector1, SelectionKey.OP_ACCEPT);
        System.out.println("selectionKey3=" + selectionKey3.hashCode());
    }
}
