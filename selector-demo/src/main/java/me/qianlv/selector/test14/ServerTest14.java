package me.qianlv.selector.test14;

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
 * 判断是否已经准备好写入
 *
 * @author xiaoshu
 */
public class ServerTest14 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        SocketChannel socketChannel = null;
        boolean isRun = true;
        while (isRun) {
            int count = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }

                if (selectionKey.isReadable()) {
                    System.out.println("server isReadable()");
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
                    int readLength = socketChannel.read(byteBuffer);
                    while (readLength != -1) {
                        byteBuffer.flip();
                        String newString = new String(byteBuffer.array(), 0, readLength);
                        System.out.println(newString);
                        byteBuffer.clear();
                        readLength = socketChannel.read(byteBuffer);
                    }
                    socketChannel.close();
                }
                iterator.remove();
            }
        }
        serverSocketChannel.close();
    }
}
