package me.qianlv.udp.test6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author xiaoshu
 */
public class Client {
    public static void main(String[] args) throws IOException {
        MulticastSocket multicastSocket = new MulticastSocket();
        byte[] byteArray = "1234567890".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length, InetAddress.getByName("224.0.0.5"), 8888);
        multicastSocket.send(datagramPacket);
        multicastSocket.close();
    }
}
