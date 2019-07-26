package me.qianlv.selector.test5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketOption;
import java.net.UnknownHostException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 获得支持的SocketOption列表
 *
 * @author xiaoshu
 */
public class Test5 {
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                Socket socket = new Socket("localhost", 8088);
                socket.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8088));
        SocketChannel socketChannel = serverSocketChannel.accept();

        Set<SocketOption<?>> set1 = serverSocketChannel.supportedOptions();
        Set<SocketOption<?>> set2 = socketChannel.supportedOptions();
        Iterator<SocketOption<?>> iterator1 = set1.iterator();
        Iterator<SocketOption<?>> iterator2 = set2.iterator();
        System.out.println("ServerSocketChannel supportedOptions:");
        while (iterator1.hasNext()) {
            SocketOption<?> socketOption = iterator1.next();
            System.out.println(socketOption.name() + " " + socketOption.getClass().getName());
        }

        System.out.println();
        System.out.println();
        System.out.println("SocketChannel supportedOptions:");
        while (iterator2.hasNext()) {
            SocketOption<?> socketOption = iterator2.next();
            System.out.println(socketOption.name() + " " + socketOption.getClass().getName());
        }
        socketChannel.close();
        serverSocketChannel.close();
    }
}
