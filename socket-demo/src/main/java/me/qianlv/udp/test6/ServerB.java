package me.qianlv.udp.test6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 使用UDP实现组播
 *
 * @author xiaoshu
 */
public class ServerB {
    public static void main(String[] args) throws IOException {
        MulticastSocket socket = new MulticastSocket(8888);
        socket.joinGroup(InetAddress.getByName("244.0.0.5"));
        byte[] byteArray = new byte[10];
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        socket.receive(datagramPacket);
        byteArray = datagramPacket.getData();
        System.out.println("ServerB " + new String(byteArray, 0, datagramPacket.getLength()));
        socket.close();
    }
}
