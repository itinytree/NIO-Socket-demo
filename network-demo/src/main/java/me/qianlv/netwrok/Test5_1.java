package me.qianlv.netwrok;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 根据主机名获得所有的IP地址
 *
 * @author xiaoshu
 */
public class Test5_1 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress[] myAddressArray = InetAddress.getAllByName("xiaoshu-pc.local");
        InetAddress[] baiduAddressArray = InetAddress.getAllByName("www.baidu.com");
        InetAddress[] ipStringAddressArray = InetAddress.getAllByName("192.168.99.233");
        for (int i = 0; i < myAddressArray.length; i++) {
            InetAddress myAddress = myAddressArray[i];
            System.out.println("myAddress.getHostAddress()=" + myAddress.getHostAddress() + " " + myAddress.getClass().getName());
        }
        System.out.println();
        for (InetAddress baiduAddress : baiduAddressArray) {
            System.out.println("baiduAddress.getHostAddress()=" + baiduAddress.getHostAddress() + " " + baiduAddress.getClass().getName());
        }
        System.out.println();
        for (InetAddress ipStringAddress : ipStringAddressArray) {
            System.out.println("ipStringAddress.getHostAddress()=" + ipStringAddress.getHostAddress() + " " + ipStringAddress.getClass().getName());
        }
    }
}
