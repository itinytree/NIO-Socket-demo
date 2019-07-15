package me.qianlv.socket.test7;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用Socket传递图片
 *
 * @author xiaoshu
 */
public class Server7 {
    public static void main(String[] args) throws IOException {
        byte[] byteArray = new byte[1024];
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/xiaoshu/logs/newtest.jpg"));
        int readLength = inputStream.read(byteArray);
        while (readLength != -1) {
            outputStream.write(byteArray, 0, readLength);
            readLength = inputStream.read(byteArray);
        }
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();

    }
}
