package me.qianlv.aio.file.test3;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * CompletionHandler接口的使用
 *
 * @author xiaoshu
 */
public class Test8 {
    public static void main(String[] args) throws InterruptedException, IOException {
        Path path = Paths.get("/Users/xiaoshu/Downloads/beccari.jpg");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        System.out.println("begin time = " + System.currentTimeMillis());
        channel.lock("我是附加值", new CompletionHandler<FileLock, String>() {
            @Override
            public void completed(FileLock result, String attachment) {
                try {
                    System.out.println("public void completed(FileLock result, String attachment) attachment = " + attachment);
                    result.release();
                    channel.close();
                    System.out.println("release and close");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, String attachment) {
                System.out.println("public void failed(Throwable exc, String attachment) attachment = " + attachment);
                System.out.println("getMessage = " + exc.getMessage());
            }
        });
        System.out.println("    end time = " + System.currentTimeMillis());
        Thread.sleep(3000);
    }
}
