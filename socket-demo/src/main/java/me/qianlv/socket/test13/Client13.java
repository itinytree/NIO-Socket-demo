package me.qianlv.socket.test13;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client13 {
    public static void main(String[] args) throws InterruptedException, IOException {
        Socket socket = new Socket("localhost", 8088);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
