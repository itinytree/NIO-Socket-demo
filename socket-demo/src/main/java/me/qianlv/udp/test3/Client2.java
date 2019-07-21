package me.qianlv.udp.test3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Client2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("localhost", 8888));
        String sendString = "我是员工";
        byte[] byteArray = sendString.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(new byte[]{}, 0);
        datagramPacket.setData(byteArray, 3, 9);
        socket.send(datagramPacket);
        socket.close();
    }
}
