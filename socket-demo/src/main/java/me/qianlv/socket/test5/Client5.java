package me.qianlv.socket.test5;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 服务端向客户端传递字符串
 *
 * @author xiaoshu
 */
public class Client5 {
    public static void main(String[] args) throws IOException {
        System.out.println("client 连接准备=" + System.currentTimeMillis());
        Socket socket = new Socket("localhost", 8088);
        System.out.println("client 连接结束=" + System.currentTimeMillis());
        char[] charArr = new char[3];
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        System.out.println("serverB begin " + System.currentTimeMillis());
        int readLength = inputStreamReader.read(charArr);
        while (-1 != readLength) {
            System.out.println(new String(charArr, 0, readLength));
            readLength = inputStreamReader.read(charArr);
        }
        System.out.println();
        inputStreamReader.close();
        inputStream.close();
        socket.close();
        System.out.println("client运行结束= " + System.currentTimeMillis());

    }
}
