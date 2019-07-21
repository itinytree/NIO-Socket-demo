package me.qianlv.udp.test4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 使用UDP实现单播
 *
 * @author xiaoshu
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        byte[] byteArray = new byte[10];
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        datagramSocket.receive(datagramPacket);
        byteArray = datagramPacket.getData();
        System.out.println(new String(byteArray, 0, datagramPacket.getLength()));
        datagramSocket.close();
    }
}
