package me.qianlv.socket.test6;

import java.io.*;
import java.net.Socket;

/**
 * 实现服务端与客户端多次的往来通信
 */
public class Client6 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8088);
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        //输出开始
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        String strA = "服务端你好A\n";
        String strB = "服务端你好B\n";
        String strC = "服务端你好C\n";
        int allStringLength = (strA + strB + strC).getBytes().length;
        objectOutputStream.writeInt(allStringLength);
        objectOutputStream.flush();

        objectOutputStream.write(strA.getBytes());
        objectOutputStream.write(strB.getBytes());
        objectOutputStream.write(strC.getBytes());
        objectOutputStream.flush();
        //输出结束

        //输入开始
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        int byteLength = objectInputStream.readInt();
        byte[] byteArray = new byte[byteLength];
        objectInputStream.readFully(byteArray);
        String newString = new String(byteArray);
        System.out.println(newString);
        //输入结束

        //输出开始
        strA = "服务端你好D\n";
        strB = "服务端你好E\n";
        strC = "服务端你好F\n";
        allStringLength = (strA + strB + strC).getBytes().length;
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

        objectInputStream.close();
        objectOutputStream.close();
        socket.close();
    }
}
