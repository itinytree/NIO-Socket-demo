package me.qianlv.socket.test12;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client12 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8088);
        InputStream inputStream = socket.getInputStream();
        byte[] byteArray = new byte[1000];
        int readLength = inputStream.read(byteArray);
        while (readLength != -1) {
            System.out.println(new String(byteArray, 0, readLength));
            readLength = inputStream.read(byteArray);
        }
        inputStream.close();
        socket.close();
    }
}
