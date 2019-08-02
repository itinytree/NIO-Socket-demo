package me.qianlv.selector.test14;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ClientTest14 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost", 8888));
        boolean isRun = true;
        while (isRun) {
            int keyCount = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey1 = iterator.next();
                System.out.println("判读是否为同一key");
                System.out.println(selectionKey == selectionKey1);
                if (selectionKey1.isConnectable()) {
                    System.out.println("client isConnectable()");
                    if (socketChannel.isConnectionPending()) {
                        while (!socketChannel.finishConnect()) {
                            System.out.println("!socketChannel.finishConnect()--------");
                        }
                    }
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }

                if (selectionKey1.isWritable()) {
                    System.out.println("client isWritable()");
                    byte[] writeData = "我来自客户端，您好，服务器！".getBytes();
                    ByteBuffer byteBuffer = ByteBuffer.wrap(writeData);
                    socketChannel.write(byteBuffer);
                    socketChannel.close();
                }
            }
        }
        System.out.println("client end!");
    }
}
