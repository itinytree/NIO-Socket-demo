package me.qianlv.selector.test12;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 从已就绪的键集中获得通道中的数据
 *
 * @author xiaoshu
 */
public class ServerTest12 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        boolean isRun = true;
        while (isRun) {
            int keyCount = selector.select();
            Set<SelectionKey> set = selector.selectedKeys();
            Iterator<SelectionKey> iterator = set.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    ServerSocket serverSocket = channel.socket();
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    byte[] byteArray = new byte[100];
                    int readLength = inputStream.read(byteArray);
                    while (readLength != -1) {
                        String newString = new String(byteArray, 0, readLength);
                        System.out.println(newString);
                        readLength = inputStream.read(byteArray);
                    }
                    inputStream.close();
                    socket.close();
                    iterator.remove();
                }
            }

        }
        serverSocketChannel.close();
    }
}
