package me.qianlv.socket.test15;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 *
 * @author xiaoshu
 */
public class Test1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        Thread.sleep(Integer.MAX_VALUE);
        socket.close();
        serverSocket.close();
    }
}
