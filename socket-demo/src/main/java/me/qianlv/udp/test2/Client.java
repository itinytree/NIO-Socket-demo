package me.qianlv.udp.test2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        //客户端要发送的数据字节长度为 10
        //所以服务端只能最大取得 10 个数据
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("localhost", 8088));
        String sendString = "";
        for (int i = 0; i < 65507 - 3; i++) {
            sendString = sendString + "a";
        }
        sendString = sendString + "end";
        sendString = sendString + "end";
        System.out.println(sendString);
        DatagramPacket datagramPacket = new DatagramPacket(sendString.getBytes(), sendString.getBytes().length);
        socket.send(datagramPacket);
        socket.close();
    }
}
