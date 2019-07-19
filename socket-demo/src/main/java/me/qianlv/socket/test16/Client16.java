package me.qianlv.socket.test16;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client16 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("client begin");
        Socket socket = new Socket("localhost", 8088);
        System.out.println("a= " + System.currentTimeMillis());
        socket.setKeepAlive(true);
        System.out.println("b=" + System.currentTimeMillis());
        System.out.println("client end");
        Thread.sleep(Integer.MAX_VALUE);
        socket.close();
    }
}
