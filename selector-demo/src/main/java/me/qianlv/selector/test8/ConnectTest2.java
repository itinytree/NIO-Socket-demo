package me.qianlv.selector.test8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * 以非阻塞模式进行连接操作
 *
 * @author xiaoshu
 */
public class ConnectTest2 {
    public static void main(String[] args) {
        long beginTime = 0, endTime = 0;
        boolean connectResult = false;
        try {
            SocketChannel socketChannel = SocketChannel.open();
            beginTime = System.currentTimeMillis();
            //SocketChannel是非阻塞模式
            socketChannel.configureBlocking(false);
            System.out.println(socketChannel.isConnectionPending());
            connectResult = socketChannel.connect(new InetSocketAddress("localhost", 8888));
            System.out.println(socketChannel.isConnectionPending());
            endTime = System.currentTimeMillis();
            System.out.println("正常连接耗时:" + (endTime - beginTime) + " connectResult=" + connectResult);
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            endTime = System.currentTimeMillis();
            System.out.println("异常连接耗时:" + (endTime - beginTime) + " connectResult=" + connectResult);
        }
    }
}
