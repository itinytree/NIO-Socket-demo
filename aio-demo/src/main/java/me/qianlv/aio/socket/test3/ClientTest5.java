package me.qianlv.aio.socket.test3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

/**
 * 正常写操作的客户端
 *
 * @author xiaoshu
 */
public class ClientTest5 {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8088), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
                for (int i = 0; i < 1000 - 3; i++) {
                    byteBuffer.put("1".getBytes());
                }
                byteBuffer.put("end".getBytes());
                byteBuffer.flip();
                socketChannel.write(byteBuffer, 1, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer result, Void attachment) {
                        try {
                            socketChannel.close();
                            System.out.println("client close");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        System.out.println("write public void failed(Throwable exc, Void attachment)");
                        System.out.println("exc getMessage() = " + exc.getMessage());
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("connect public void failed(Throwable exc, Void attachment)");
                System.out.println("exc getMessage() = " + exc.getMessage());
            }
        });
        Thread.sleep(5000);
    }
}
