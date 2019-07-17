package me.qianlv.socket.test12;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * public void shutdownOutput()屏蔽输出流(OutputStream)
 *
 * @author xiaoshu
 */
public class Server12 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("123".getBytes());
        socket.shutdownOutput(); //终止序列
        socket.getOutputStream(); //出现异常
        // outputStream.write("456".getBytes()); //出现异常
        socket.close();
        serverSocket.close();
    }
}
