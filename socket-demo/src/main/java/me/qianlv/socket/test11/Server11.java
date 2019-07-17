package me.qianlv.socket.test11;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * public void shutdownInput()屏蔽输入流(InputStream)的效果
 *
 * @author xiaoshu
 */
public class Server11 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        System.out.println("A=" + inputStream.available());
        byte[] byteArray = new byte[2];
        int readLength = inputStream.read(byteArray);
        System.out.println("server取得的数据:" + new String(byteArray, 0, readLength));
        socket.shutdownInput();//屏蔽InputStream，到达流的结尾
        System.out.println("B=" + inputStream.available()); //静默丢弃其他数据
        readLength = inputStream.read(byteArray); //-1
        System.out.println("readLength=" + readLength);
        //再次调用getInputStream方法出现异常
        //java.net.SocketException: Socket input is shutdown
        socket.getInputStream();
        socket.close();
        serverSocket.close();
    }
}
