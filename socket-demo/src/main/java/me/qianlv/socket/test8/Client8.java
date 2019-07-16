package me.qianlv.socket.test8;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Client8 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8088);
//        socket.setSoTimeout(10000);
        InputStream inputStream = socket.getInputStream();
        byte[] byteArray = new byte[2048];
        int readLength = inputStream.read(byteArray);
        new String(byteArray, 0, readLength);
        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        int i = 0;
        while (true) {
            bufferedOutputStream.write(("Hello World!" + i).getBytes());
            bufferedOutputStream.flush();
            TimeUnit.MILLISECONDS.sleep(500);
            i++;
            if (i > 10000000) {
                break;
            }
        }
        bufferedOutputStream.close();
        outputStream.close();
        socket.close();
    }
}
