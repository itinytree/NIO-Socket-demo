package me.qianlv.aio.test1;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 实现重叠锁定
 *
 * @author xiaoshu
 */
public class Test17 {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Path path = Paths.get("/Users/xiaoshu/Downloads/spring.pdf");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        Future<FileLock> future = channel.lock(0, 3, false);
        FileLock fileLock = future.get();
        System.out.println("A    get lock time = " + System.currentTimeMillis());
        Thread.sleep(8000);
        fileLock.release();
        System.out.println("A    release lock time = " + System.currentTimeMillis());
        channel.close();
    }
}
