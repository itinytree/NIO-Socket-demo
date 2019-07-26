package me.qianlv.selector.test9;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * finishConnect()
 *
 * @author xiaoshu
 */
public class ConnectTest {
    public static void main(String[] args) throws IOException {
        long beginTime = 0;
        long endTime = 0;
        SocketChannel socketChannel = SocketChannel.open();
        //SocketChannel是非阻塞模式
        socketChannel.configureBlocking(false);
        //connect()方法的返回值表示如果建立了连接，则为true
        //如果此通道处于非阻塞模式且连接操作正在进行中，则为false
        boolean connectResult = socketChannel.connect(new InetSocketAddress("localhost", 9999));

        new Thread(() -> {
            try {
                Thread.sleep(50);
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.bind(new InetSocketAddress("localhost", 9999));
                SocketChannel channel = serverSocketChannel.accept();
                channel.close();
                serverSocketChannel.close();
                System.out.println("server end!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        if (!connectResult) {
            System.out.println("connectResult == false");
            while (!socketChannel.finishConnect()) {
                System.out.println("一直在尝试连接");
            }
        }
        socketChannel.close();
    }
}
