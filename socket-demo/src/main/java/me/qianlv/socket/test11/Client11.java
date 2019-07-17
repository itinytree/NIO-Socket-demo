package me.qianlv.socket.test11;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client11 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8088);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("abcdefg".getBytes());
        socket.close();
    }
}
