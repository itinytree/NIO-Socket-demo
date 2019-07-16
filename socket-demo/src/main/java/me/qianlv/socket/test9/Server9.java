package me.qianlv.socket.test9;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * 设置超时时间4s，验证在4s内没有客户端连接时服务端将出现超时异常
 *
 * @author xiaoshu
 */
public class Server9 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println(serverSocket.getSoTimeout());
        serverSocket.setSoTimeout(4000);
        System.out.println(serverSocket.getSoTimeout());
        System.out.println();
        System.out.println("begin " + System.currentTimeMillis());
        serverSocket.accept();
        System.out.println("end " + System.currentTimeMillis());
    }
}
