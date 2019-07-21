package me.qianlv.udp.test4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author xiaoshu
 */
public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        datagramSocket.connect(new InetSocketAddress("localhost", 8888));
        byte[] byteArray = "1234567890".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        datagramSocket.send(datagramPacket);
        datagramSocket.close();
    }
}
