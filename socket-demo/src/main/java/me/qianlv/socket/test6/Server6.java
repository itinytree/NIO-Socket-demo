package me.qianlv.socket.test6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现服务端与客户端多次的往来通信
 *
 * @author xiaoshu
 */
public class Server6 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Socket socket = serverSocket.accept();
        //输入开始
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        int byteLength = objectInputStream.readInt();
        byte[] byteArray = new byte[byteLength];
        objectInputStream.readFully(byteArray);
        String newString = new String(byteArray);
        System.out.println(newString);
        //输入结束
        //输出开始
        OutputStream outputStream = socket.getOutputStream();
        String strA = "客户端你好A\n";
        String strB = "客户端你好B\n";
        String strC = "客户端你好C\n";

        int allStringLength = (strA + strB + strC).getBytes().length;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeInt(allStringLength);
        objectOutputStream.flush();

        objectOutputStream.write(strA.getBytes());
        objectOutputStream.write(strB.getBytes());
        objectOutputStream.write(strC.getBytes());
        objectOutputStream.flush();
        //输出结束

        //输入开始
        byteLength = objectInputStream.readInt();
        byteArray = new byte[byteLength];
        objectInputStream.readFully(byteArray);
        newString = new String(byteArray);
        System.out.println(newString);
        //输入结束

        //输出开始
        strA = "客户端你好D\n";
        strB = "客户端你好E\n";
        strC = "客户端你好F\n";
        allStringLength = (strA + strB + strC).getBytes().length;
        objectOutputStream.writeInt(allStringLength);
        objectOutputStream.flush();

        objectOutputStream.write(strA.getBytes());
        objectOutputStream.write(strB.getBytes());
        objectOutputStream.write(strC.getBytes());
        objectOutputStream.flush();
        //输出结束
        objectOutputStream.close();
        objectInputStream.close();
        serverSocket.close();
    }
}
