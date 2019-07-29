package me.qianlv.selector.test12;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class ClientTest12 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我是中国人，我来自客户端！".getBytes());
        socket.close();
    }
}
