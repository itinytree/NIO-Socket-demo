package me.qianlv.udp.test2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 测试发送超大数量的包导致数据截断
 *
 * @author xiaoshu
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8088);
        byte[] byteArray = new byte[65507];
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        System.out.println("receive begin" + System.currentTimeMillis());
        datagramSocket.receive(datagramPacket);
        System.out.println("receive end" + System.currentTimeMillis());
        datagramSocket.close();
        System.out.println("服务端接收到的数据长度为:" + datagramPacket.getLength());
        System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
    }
}
