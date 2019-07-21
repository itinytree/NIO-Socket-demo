package me.qianlv.udp.test5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Server1类是在计算机A中运行的
 *
 * @author xiaoshu
 */
public class Server1 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(7777);
        byte[] byteArray = new byte[10];
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        socket.receive(datagramPacket);
        socket.close();
        byteArray = datagramPacket.getData();
        System.out.println(new String(byteArray, 0, datagramPacket.getLength()));
    }
}
