package me.qianlv.selector.test6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;

/**
 * @author xiaoshu
 */
public class Test6 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        System.out.println("A SO_RCVBUF=" + serverSocketChannel.getOption(StandardSocketOptions.SO_RCVBUF));
        serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 5678);
        System.out.println("B SO_RCVBUF=" + serverSocketChannel.getOption(StandardSocketOptions.SO_RCVBUF));

        serverSocketChannel.bind(new InetSocketAddress("localhost", 8888));
        InetSocketAddress socketAddress = (InetSocketAddress) serverSocketChannel.getLocalAddress();
        System.out.println(socketAddress.getHostString());
        System.out.println(socketAddress.getPort());
    }
}