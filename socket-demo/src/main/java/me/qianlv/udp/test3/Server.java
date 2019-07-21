package me.qianlv.udp.test3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * DatagramPacket类中的常用API
 * <p>
 * 编码：UTF-8
 *
 * @author xiaoshu
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        byte[] byteArray = new byte[10];
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        System.out.println("receive begin" + System.currentTimeMillis());
        datagramSocket.receive(datagramPacket);
        System.out.println("receive end" + System.currentTimeMillis());
        datagramSocket.close();
        byteArray = datagramPacket.getData();
        System.out.println(new String(byteArray, 0, datagramPacket.getLength()));
    }
}
