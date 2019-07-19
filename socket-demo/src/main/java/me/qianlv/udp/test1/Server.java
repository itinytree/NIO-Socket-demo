package me.qianlv.udp.test1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP发送数据
 *
 * @author xiaoshu
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        byte[] byteArray = new byte[12];
        //构造方法第 2 个参数也要写上 10 个，代表要接收的数据长度为 10
        //和客户端发送数据的长度要一致
        DatagramPacket datagramPacket = new DatagramPacket(byteArray, 10);
        datagramSocket.receive(datagramPacket);
        datagramSocket.close();
        System.out.println("包中数据的长度:" + datagramPacket.getLength());
        System.out.println(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
    }
}
