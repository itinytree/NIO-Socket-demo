package me.qianlv.netwrok;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * InterfaceAddress使用
 *
 * @author xiaoshu
 */
public class Test6 {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            System.out.println("获得网络设备名称=" + networkInterface.getName());
            System.out.println("获得网络设备显示名称=" + networkInterface.getDisplayName());
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                InetAddress address = interfaceAddress.getAddress();
                if (null != address) {
                    System.out.println("        interfaceAddress.getAddress()=" + address.getHostAddress());
                }
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (null != broadcast) {
                    System.out.println("        interfaceAddress.getBroadcast()=" + broadcast.getHostAddress());
                }
                System.out.println("        getNetworkPrefixLength=" + interfaceAddress.getNetworkPrefixLength());
                System.out.println();
            }
            System.out.println();
        }
    }
}
