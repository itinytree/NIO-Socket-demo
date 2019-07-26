package me.qianlv.selector.test8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * 以阻塞模式进行连接操作
 *
 * @author xiaoshu
 */
public class ConnectTest1 {
    public static void main(String[] args) {
        long beginTime = 0, endTime = 0;
        boolean connectResult = false;
        SocketChannel socketChannel = null;
        try {
            //SocketChannel是阻塞模式
            //在发生错误或连接到目标之前，connect()方法一直是阻塞的
            socketChannel = SocketChannel.open();
            System.out.println(socketChannel.isConnectionPending());
            beginTime = System.currentTimeMillis();
            connectResult = socketChannel.connect(new InetSocketAddress("localhost", 8888));
            endTime = System.currentTimeMillis();
            System.out.println("正常连接耗时:" + (endTime - beginTime) + " connectResult=" + connectResult);
            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
            endTime = System.currentTimeMillis();
            System.out.println("异常连接耗时:" + (endTime - beginTime) + " connectResult=" + connectResult);
            System.out.println(socketChannel.isConnectionPending());
        }
    }
}
