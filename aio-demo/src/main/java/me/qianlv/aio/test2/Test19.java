package me.qianlv.aio.test2;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 返回此通道文件当前大小与通道打开状态
 *
 * @author xiaoshu
 */
public class Test19 {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Path path = Paths.get("/Users/xiaoshu/Downloads/spring.pdf");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        System.out.println("File size = " + channel.size());
        System.out.println("A isOpen = " + channel.isOpen());
        channel.close();
        System.out.println("B isOpen = " + channel.isOpen());
    }
}
