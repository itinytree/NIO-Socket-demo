package me.qianlv.aio.socket.test2;

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
public class ClientTest3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8088), null, new CompletionHandler<Void, Void>() {
            @Override
            public void completed(Void result, Void attachment) {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100000);
                    for (int i = 0; i < 1000 - 3; i++) {
                        byteBuffer.put("1".getBytes());
                    }
                    byteBuffer.put("end".getBytes());
                    byteBuffer.flip();
                    int writeSum = 0;
                    //由于write()方法是异步的,所以执行write()方法后
                    //并不能100%将数据写出,所以得通过writeLength变量
                    //来判断具体写出多少字节的数据
                    while (writeSum < byteBuffer.limit()) {
                        Future<Integer> writeFuture = socketChannel.write(byteBuffer);
                        Integer writeLength = writeFuture.get();
                        writeSum = writeSum + writeLength;
                    }
                    socketChannel.close();
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("connect public void failed(Throwable exc, Void attachment)");
                System.out.println("exc getMessage() = " + exc.getMessage());
            }
        });
        Thread.sleep(10000);
    }
}
