package me.qianlv.aio.socket.test3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.TimeUnit;

/**
 * 写数据
 *
 * @author xiaoshu
 */
public class ServerTest5 {
    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8088));
        serverSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel ch, Void attachment) {
                serverSocketChannel.accept(null, this); //继续下一个accept操作
                ByteBuffer byteBuffer = ByteBuffer.allocate(10000000);
                ch.read(byteBuffer, 10, TimeUnit.SECONDS, null, new CompletionHandler<Integer, Void>() {
                    @Override
                    public void completed(Integer result, Void attachment) {
                        if (result == -1) {
                            System.out.println("客户端没有传输数据就执行close了,到stream end");
                        }
                        if (result == byteBuffer.limit()) {
                            System.out.println("服务端获得客户端完整数据");
                        }
                        try {
                            ch.close();
                            System.out.println("服务端close");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Void attachment) {
                        System.out.println("public void failed(Throwable exc, Void attachment)");
                        System.out.println("exc getMessage() = " + exc.getMessage());
                    }
                });
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("accept public void failed.");
            }
        });
        while (true) {

        }
    }
}
