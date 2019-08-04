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
 * 写入方式2
 * a.txt文件的内容: 12345
 *
 * @author xiaoshu
 */
public class Test12_1 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("/Users/xiaoshu/logs/a.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        ByteBuffer byteBuffer = ByteBuffer.wrap("abcde".getBytes());
        channel.write(byteBuffer, channel.size(), "我是附加的数据", new CompletionHandler<Integer, String>() {
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
    }
}
