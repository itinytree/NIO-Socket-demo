package me.qianlv.selector.test3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client3 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我是发送的数据".getBytes());
        outputStream.close();
        socket.close();
    }
}