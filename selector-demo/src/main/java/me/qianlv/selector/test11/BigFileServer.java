package me.qianlv.selector.test11;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 传输大文件
 *
 * @author xiaoshu
 */
public class BigFileServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        SocketChannel socketChannel = null;

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        boolean isRun = true;
        while (isRun) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
                if (selectionKey.isWritable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    FileInputStream fileInputStream = new FileInputStream("/Users/xiaoshu/Downloads/MySQL实战45讲.zip");
                    FileChannel fileChannel = fileInputStream.getChannel();
                    //500M空间
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(52428000);
                    while (fileChannel.position() < fileChannel.size()) {
                        fileChannel.read(byteBuffer);
                        byteBuffer.flip();
                        while (byteBuffer.hasRemaining()) {
                            socketChannel.write(byteBuffer);
                        }
                        byteBuffer.clear();
                        System.out.println(fileChannel.position() + " " + fileChannel.size());
                    }
                    System.out.println("结束写操作");
                    socketChannel.close();
                }
            }
            serverSocketChannel.close();
        }
        serverSocketChannel.close();
    }
}
