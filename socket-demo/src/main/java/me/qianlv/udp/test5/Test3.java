package me.qianlv.udp.test5;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取计算机的广播地址
 *
 * @author xiaoshu
 */
public class Test3 {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> enm1 = NetworkInterface.getNetworkInterfaces();
        while (enm1.hasMoreElements()) {
            NetworkInterface networkInterface = enm1.nextElement();
            System.out.println(networkInterface.getName() + "  " + networkInterface.getDisplayName());
            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress interfaceAddress : interfaceAddresses) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (null != broadcast) {
                    System.out.println("    " + broadcast.getHostAddress());
                }
            }
        }

    }
}
