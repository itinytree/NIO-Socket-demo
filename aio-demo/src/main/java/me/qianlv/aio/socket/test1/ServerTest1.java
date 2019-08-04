package me.qianlv.aio.socket.test1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 接受方式1
 *
 * @author xiaoshu
 */
public class ServerTest1 {
    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel channel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8088));
        channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Void attachment) {
                channel.accept(null, this);
                System.out.println("public void completed ThreadName = " + Thread.currentThread().getName());
                ByteBuffer byteBuffer = ByteBuffer.allocate(20);
                Future<Integer> future = socketChannel.read(byteBuffer);
                try {
                    System.out.println(new String(byteBuffer.array(), 0, future.get()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("public void failed");
            }
        });
        while (true) {

        }
    }
}
