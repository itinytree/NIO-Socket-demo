package me.qianlv.socket.test14;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket选项OOBInline
 *
 * @author xiaoshu
 */
public class Server14_1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        System.out.println("server A getOOBInline=" + socket.getOOBInline());
        //服务端忽略紧急数据
        socket.setOOBInline(false);
        System.out.println("server B getOOBInline=" + socket.getOOBInline());
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] charArray = new char[1024];
        int readLength = inputStreamReader.read(charArray);
        while (readLength != -1) {
            System.out.println(new String(charArray, 0, readLength));
            readLength = inputStreamReader.read(charArray);
        }
        socket.close();
        serverSocket.close();
    }
}
