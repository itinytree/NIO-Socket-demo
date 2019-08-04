package me.qianlv.aio.file.test6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

/**
 * 读取数据方式2
 *
 * @author xiaoshu
 */
public class Test11_1 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("/Users/xiaoshu/logs/a.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        channel.read(byteBuffer, 0, "我是附加的参数", new CompletionHandler<Integer, String>() {
            @Override
            public void completed(Integer result, String attachment) {
                System.out.println("public void completed(Integer result, String attachment) result = " + result + " attachment = " + attachment);
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println("public void failed(Throwable exc, String attachment) attachment = " + attachment);
                System.out.println("getMessage = " + exc.getMessage());
                exc.printStackTrace();
            }
        });
        Thread.sleep(2000);
        channel.close();
        byte[] byteArray = byteBuffer.array();
        for (int i = 0; i < byteArray.length; i++) {
            System.out.println((char) byteArray[i]);
        }
    }
}
