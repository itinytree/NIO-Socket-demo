package me.qianlv.udp.test5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Client1类实现的不是广播的效果
 * <p>
 * 首先,在计算机A中运行Server1类,然后在计算机B中运行Server2类,最后在计算机A中运行Client1类,结果是只在计算机B中输出了字符,
 * 说明Client1类只具备单播的功能.
 *
 * @author xiaoshu
 */
public class Client1 {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("192.168.99.233", 7777));
        String newString = "12345_____";
        byte[] byteArray = newString.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, byteArray.length);
        socket.send(datagramPacket);
        socket.close();
    }
}
