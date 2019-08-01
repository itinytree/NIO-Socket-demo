package me.qianlv.selector.test16;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xiaoshu
 */
public class TestClient16 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost", 8888));
        boolean isRun = true;
        while (isRun) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isConnectable()) {
                    System.out.println("client isConnectable()");
                    if (socketChannel.isConnectionPending()) {
                        while (!socketChannel.finishConnect()) {
                            System.out.println("!socketChannel.finishConnect()---------------");
                        }
                        socketChannel.register(selector, SelectionKey.OP_WRITE, "我使用附件进行注册，我来自客户端，你好服务端！");
                    }
                }
                if (key.isWritable()) {
                    System.out.println("client isWritable()");
                    ByteBuffer byteBuffer = ByteBuffer.wrap(((String) key.attachment()).getBytes());
                    socketChannel.write(byteBuffer);
                    socketChannel.close();
                }
            }
        }
        System.out.println("client end!");
    }
}
