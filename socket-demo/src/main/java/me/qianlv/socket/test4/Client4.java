package me.qianlv.socket.test4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 客户端向服务端传递字符串
 *
 * @author xiaoshu
 */
public class Client4 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("socket begin " + System.currentTimeMillis());
        Socket socket = new Socket("localhost", 8088);
        System.out.println("socket end " + System.currentTimeMillis());
        TimeUnit.SECONDS.sleep(3);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我是外星人".getBytes());
        outputStream.close();
        socket.close();
    }
}
