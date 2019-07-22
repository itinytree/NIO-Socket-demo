package me.qianlv.selector.test1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;

/**
 * 执行绑定操作与设置backlog
 *
 * @author xiaoshu
 */
public class Server1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888), 60);
        ServerSocket serverSocket = serverSocketChannel.socket();
        Thread.sleep(5000);
        boolean isRun = true;
        while (isRun) {
            Socket socket = serverSocket.accept();
            socket.close();
        }
        Thread.sleep(8000);
        serverSocket.close();
        serverSocketChannel.close();
    }
}
