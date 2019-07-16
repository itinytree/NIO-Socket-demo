package me.qianlv.socket.test10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * backlog 的默认值
 *
 * @author xiaoshu
 */
public class Server10 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8088), 50);
        Thread.sleep(5000);
        for (int i = 0; i < 10000; i++) {
            System.out.println("accept1 begin" + (i + 1));
            Socket socket = serverSocket.accept();
            System.out.println("accept1 end" + (i + 1));
        }
        serverSocket.close();
    }
}
