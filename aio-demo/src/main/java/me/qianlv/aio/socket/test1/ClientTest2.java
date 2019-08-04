package me.qianlv.aio.socket.test1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiaoshu
 */
public class ClientTest2 {
    public static void main(String[] args) throws InterruptedException, IOException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8088), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                Future<Integer> writeFuture = socketChannel.write(ByteBuffer.wrap("我来自客户端2".getBytes()));
                try {
                    System.out.println("写入大小:" + writeFuture.get());
                    socketChannel.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {

            }
        });
        Thread.sleep(1000);
    }
}
