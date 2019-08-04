package me.qianlv.aio.file.test5;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 执行指定返回的锁定与传入附件及整合接口
 * <p>
 * public final <A> void lock(A attachment, CompletionHandler<FileLock, ? super A> handler) 方法获得不到锁,则一直等待
 *
 * @author xiaoshu
 */
public class Test10_1 {
    public static void main(String[] args) throws InterruptedException, IOException {
        Path path = Paths.get("/Users/xiaoshu/Downloads/beccari.jpg");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        System.out.println("begin time = " + System.currentTimeMillis());
        channel.lock("我是附加值B", new CompletionHandler<FileLock, String>() {
            @Override
            public void completed(FileLock result, String attachment) {
                try {
                    System.out.println("B public void completed(FileLock result, String attachment) attachment = " + attachment);
                    result.release();
                    System.out.println("B get lock time = " + System.currentTimeMillis());
                    result.release();
                    channel.close();
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
        System.out.println("B   end time = " + System.currentTimeMillis());
        Thread.sleep(50000);
    }
}
