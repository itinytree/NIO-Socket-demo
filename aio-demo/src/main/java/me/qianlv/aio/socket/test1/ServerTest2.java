package me.qianlv.aio.socket.test1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 接受方式2
 *
 * @author xiaoshu
 */
public class ServerTest2 {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8088));
        System.out.println("A " + System.currentTimeMillis());
        Future<AsynchronousSocketChannel> socketChannelFuture = serverSocketChannel.accept();
        System.out.println("B " + System.currentTimeMillis());
        AsynchronousSocketChannel asynchronousSocketChannel = socketChannelFuture.get();
        System.out.println("C " + System.currentTimeMillis());
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        System.out.println("D " + System.currentTimeMillis());
        Future<Integer> readFuture = asynchronousSocketChannel.read(byteBuffer);
        System.out.println("E " + System.currentTimeMillis());
        System.out.println(new String(byteBuffer.array(), 0, readFuture.get()));
        System.out.println("F " + System.currentTimeMillis());
        Thread.sleep(4000);
    }
}
