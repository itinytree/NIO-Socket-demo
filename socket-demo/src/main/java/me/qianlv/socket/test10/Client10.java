package me.qianlv.socket.test10;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client10 {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10000; i++) {
            Socket socket = new Socket("localhost", 8088);
            System.out.println("client发起连接次数:" + (i + 1));
        }
    }
}
