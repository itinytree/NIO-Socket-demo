package me.qianlv.socket.test8;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoshu
 */
public class Server8 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8088));
        serverSocket.setSoTimeout(15000);
        System.out.println("accept begin " + System.currentTimeMillis());
        Socket socket = serverSocket.accept();
        System.out.println("accept end " + System.currentTimeMillis());
        socket.setSoTimeout(10000);
        InputStream inputStream1 = socket.getInputStream();
        byte[] byteArray = new byte[2048];
        int readLength = inputStream1.read(byteArray);
        new String(byteArray, 0, readLength);
        TimeUnit.SECONDS.sleep(20);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("Hello Client".getBytes());
        outputStream.flush();
        InputStream inputStream = socket.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] byteArray2 = new byte[1024];
        int readLength1 = bufferedInputStream.read(byteArray);
        while (readLength != -1) {
            String newString = new String(byteArray, 0, readLength);
            System.out.println(newString);
            readLength = bufferedInputStream.read(byteArray);
        }
        outputStream.close();
        bufferedInputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
