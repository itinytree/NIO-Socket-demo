package me.qianlv.socket.test5;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端向客户端传递字符串
 *
 * @author xiaoshu
 */
public class Server5 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("server 阻塞开始=" + System.currentTimeMillis());
        Socket socket = serverSocket.accept();
        System.out.println("server 阻塞结束=" + System.currentTimeMillis());
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我是小树，我来自server端！".getBytes());
        outputStream.close();
        socket.close();
        serverSocket.close();
    }
}
