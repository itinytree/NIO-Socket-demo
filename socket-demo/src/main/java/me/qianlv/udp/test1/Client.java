package me.qianlv.udp.test1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        //客户端要发送的数据字节长度为 10
        //所以服务端只能最大取得 10 个数据
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("localhost", 8888));
        String newString = "1234567890";
        byte[] byteArray = newString.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        socket.send(datagramPacket);
        socket.close();
    }
}
