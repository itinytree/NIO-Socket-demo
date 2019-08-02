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
 * 使用DatagramChannel类实现UDP通信
 *
 * @author xiaoshu
 */
public class ServerTest16 {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        datagramChannel.bind(new InetSocketAddress("localhost", 8888));
        Selector selector = Selector.open();
        SelectionKey selectionKey = datagramChannel.register(selector, SelectionKey.OP_READ);
        boolean isRun = true;
        while (isRun) {
            int keyCount = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    datagramChannel = (DatagramChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
                    datagramChannel.receive(byteBuffer);
                    System.out.println(new String(byteBuffer.array(), 0, byteBuffer.position()));
                }
                iterator.remove();
            }
        }
        datagramChannel.close();
    }
}
