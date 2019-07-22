package me.qianlv.selector.test1;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client1 {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 100; i++) {
            Socket socket = new Socket("localhost", 8888);
            socket.close();
            System.out.println("客户端连接个数为:" + (i + 1));
        }
    }
}
