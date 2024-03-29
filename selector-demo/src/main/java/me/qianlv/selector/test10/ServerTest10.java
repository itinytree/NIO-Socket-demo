package me.qianlv.selector.test10;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * FileChannel类中的long transferTo(postion, count, WritableByteChannel)方法的使用
 * 此方法的调用不一定传输所有请求的字节
 *
 * @author xiaoshu
 */
public class ServerTest10 {
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
                    //此文件约2.1GB
                    RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/xiaoshu/software/ubuntu-19.04-desktop-amd64.iso", "rw");
                    System.out.println("file.length()=" + randomAccessFile.length());
                    FileChannel channel = randomAccessFile.getChannel();
                    channel.transferTo(0, randomAccessFile.length(), socketChannel);
                    channel.close();
                    randomAccessFile.close();
                    socketChannel.close();
                }
            }
        }
        serverSocketChannel.close();
    }
}
