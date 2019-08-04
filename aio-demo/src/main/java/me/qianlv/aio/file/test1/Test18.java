package me.qianlv.aio.file.test1;

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
public class Test18 {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Path path = Paths.get("/Users/xiaoshu/Downloads/spring.pdf");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        System.out.println("lock begin " + System.currentTimeMillis());
        Future<FileLock> future = channel.lock(1, 5, false);
        System.out.println("lock end = " + System.currentTimeMillis());
        FileLock fileLock = future.get();
        System.out.println("B   get lock time = " + System.currentTimeMillis());
        fileLock.release();
        channel.close();
    }
}
