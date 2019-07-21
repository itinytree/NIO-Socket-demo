package me.qianlv.udp.test5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Client2 实现广播的效果
 * <p>
 * 首先,在计算机A中运行Server1类,然后在计算机B中运行Server2类,最后在计算机A中运行Client2类,结果计算机A和计算机B都接收到了UDP广播的信息
 *
 * @author xiaoshu
 */
public class Client2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("192.168.99.255", 7777));
        String newString = "_____12345";
        byte[] byteArray = newString.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        socket.send(datagramPacket);
        socket.close();
    }
}
