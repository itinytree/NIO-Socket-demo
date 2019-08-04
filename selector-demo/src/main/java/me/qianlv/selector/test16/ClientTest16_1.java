package me.qianlv.selector.test16;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * 连接操作
 *
 * @author xiaoshu
 */
public class ClientTest16_1 {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.connect(new InetSocketAddress("localhost", 8888));
        Selector selector = Selector.open();
        SelectionKey selectionKey = datagramChannel.register(selector, SelectionKey.OP_WRITE);
        int keyCount = selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isWritable()) {
                ByteBuffer byteBuffer = ByteBuffer.wrap("我来自客户端".getBytes());
                datagramChannel.write(byteBuffer);
                datagramChannel.close();
            }
        }
        System.out.println("client end !");

    }
}