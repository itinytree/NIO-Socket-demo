package me.qianlv.socket.test16;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 选项 KeepAlive
 *
 * @author xiaoshu
 */
public class Server16 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("server begin");
        Socket socket = serverSocket.accept();
        System.out.println("server end");
        Thread.sleep(Integer.MAX_VALUE);
        socket.close();
        serverSocket.close();
    }
}
