package me.qianlv.netwrok;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 根据主机名获得IP地址
 *
 * @author xiaoshu
 */
public class Test5 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress myAddress = InetAddress.getByName("xiaoshu-pc.local");
        InetAddress baiduAddrss = InetAddress.getByName("www.baidu.com");
        InetAddress ipStringAddrss = InetAddress.getByName("192.168.99.233");
        InetAddress localhostAddress = InetAddress.getByName("localhost");
        System.out.println(localhostAddress.getClass().getName() + " " + localhostAddress.getHostAddress());
        System.out.println(myAddress.getClass().getName() + " " + myAddress.getHostAddress());
        System.out.println(baiduAddrss.getClass().getName() + " " + baiduAddrss.getHostAddress());
        System.out.println(ipStringAddrss.getClass().getName() + " " + ipStringAddrss.getHostAddress());
    }
}
