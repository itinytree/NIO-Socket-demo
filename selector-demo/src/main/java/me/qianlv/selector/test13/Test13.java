package me.qianlv.selector.test13;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 对SelectionKey执行cancel()方法后的效果
 *
 * @author xiaoshu
 */
public class Test13 {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel1 = ServerSocketChannel.open();
        serverSocketChannel1.bind(new InetSocketAddress("localhost", 7777));
        serverSocketChannel1.configureBlocking(false);

        ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
        serverSocketChannel2.bind(new InetSocketAddress("localhost", 8888));
        serverSocketChannel2.configureBlocking(false);

        Selector selector = Selector.open();
        SelectionKey selectionKey1 = serverSocketChannel1.register(selector, SelectionKey.OP_ACCEPT);
        SelectionKey selectionKey2 = serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);

        Thread client = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 7777);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("我是中国人，我来自客户端!to7777".getBytes());
                socket.close();

                Socket socket2 = new Socket("localhost", 8888);
                OutputStream outputStream2 = socket2.getOutputStream();
                outputStream2.write("我是中国人，我来自客户端!to8888".getBytes());
                socket2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        client.start();
        Thread getInfo = new Thread(() -> {
            try {
                Thread.sleep(10000);
                System.out.println("------getInfo----");
                Set<SelectionKey> keys = selector.keys();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("select()方法执行第2次后的信息");
                System.out.println("keys.size() = " + keys.size());
                System.out.println("selectionKeys.size() = " + selectionKeys.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        getInfo.start();

        //目的是先让客户端连接服务端
        Thread.sleep(1000);

        boolean isRun = true;
        while (isRun) {
            System.out.println("isRun = " + isRun);
            int keyCount = selector.select();
            Set<SelectionKey> keys = selector.keys();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("取消之前的信息：");
            System.out.println("keys.size() = " + keys.size());
            System.out.println("selectionKeys.size() = " + selectionKeys.size());
            System.out.println();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                System.out.println("iterator-------");
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    ServerSocket serverSocket = channel.socket();
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    byte[] byteArray = new byte[1000];
                    int readLength = inputStream.read(byteArray);
                    while (readLength != -1) {
                        String string = new String(byteArray, 0, readLength);
                        System.out.println(string);
                        readLength = inputStream.read(byteArray);
                    }
                    inputStream.close();
                    socket.close();
//                    iterator.remove();
                    if (serverSocket.getLocalPort() == 7777) {
                        selectionKey.cancel();
                        System.out.println("取消之后的信息:");
                        System.out.println("keys.size() = " + keys.size());
                        System.out.println("selectionKeys.size() = " + selectionKeys.size());
                    }
                }
            }
        }
        serverSocketChannel1.close();
        serverSocketChannel2.close();
    }
}