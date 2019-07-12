package me.qianlv.netwrok;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Test1 {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            System.out.println("getName获得网络设备名称=" + networkInterface.getName());
            System.out.println("getDisplayName获得网络设备显示名称=" + networkInterface.getDisplayName());
            System.out.println("getIndex获得网络接口的索引=" + networkInterface.getIndex());
            System.out.println("isUp是否已经开启并运行=" + networkInterface.isUp());
            System.out.println("isLoopback是否为回调接口=" + networkInterface.isLoopback());
        }
    }
}
