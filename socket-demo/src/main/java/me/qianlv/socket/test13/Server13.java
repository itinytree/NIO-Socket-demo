package me.qianlv.socket.test13;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket选项Timeout
 *
 * @author xiaoshu
 */
public class Server13 {
    public static void main(String[] args) throws IOException {
        //setSoTimeout 设置超时时间为 5s
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        System.out.println("setSoTimeout befor " + socket.getSoTimeout());
        socket.setSoTimeout(5000);
        System.out.println("setSoTimeout after " + socket.getSoTimeout());
        InputStream inputStream = socket.getInputStream();
        byte[] byteArray = new byte[1024];
        System.out.println("read begin_ " + System.currentTimeMillis());
        int readLength = inputStream.read(byteArray);
        System.out.println("read end: " + System.currentTimeMillis());
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
