package me.qianlv.socket.test9;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client9 {
    public static void main(String[] args) throws IOException {
        System.out.println("client begin" + System.currentTimeMillis());
        Socket socket = new Socket("localhost", 8088);
        System.out.println("client end" + System.currentTimeMillis());
    }
}
