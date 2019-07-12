package me.qianlv.netwrok;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 获取网卡信息
 *
 * @author xiaoshu
 */
public class Test3 {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            System.out.println("父接口的hashCode=" + networkInterface.hashCode());
            System.out.println("getName 获得网络设备名称" + networkInterface.getName());
            System.out.println("getDisplayName 获得网络设备显示名称=" + networkInterface.getDisplayName());
            System.out.println("isVirtual 是否为虚拟接口=" + networkInterface.isVirtual());
            System.out.println("getParent 获得父接口=" + networkInterface.getParent());
            System.out.println("取得子接口信息=");
            System.out.println();
            Enumeration<NetworkInterface> subInterfaces = networkInterface.getSubInterfaces();
            while (subInterfaces.hasMoreElements()) {
                NetworkInterface subNetworkInterface = subInterfaces.nextElement();
                System.out.println(subNetworkInterface.getName());
                System.out.println(subNetworkInterface.getDisplayName());
                System.out.println(subNetworkInterface.isVirtual());
                System.out.println(subNetworkInterface.getParent().hashCode());
                System.out.println();
            }
        }
    }
}
