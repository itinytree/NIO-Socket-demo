package me.qianlv.socket.test7;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 使用Socket传递图片
 *
 * @author xiaoshu
 */
public class Client7 {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xiaoshu/logs/test.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] byteArray = new byte[2018];
        System.out.println("socket begin " + System.currentTimeMillis());
        Socket socket = new Socket("localhost", 8088);
        System.out.println("socket end " + System.currentTimeMillis());

        OutputStream outputStream = socket.getOutputStream();
        int readLength = inputStream.read(byteArray);
        while (readLength != -1) {
            outputStream.write(byteArray, 0, readLength);
            readLength = inputStream.read(byteArray);
        }


        outputStream.close();
        inputStream.close();
        socket.close();

    }
}
