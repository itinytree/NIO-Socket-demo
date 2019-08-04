package me.qianlv.aio.file.test6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 写入方式1
 * a.txt文件的内容: 12345
 *
 * @author xiaoshu
 */
public class Test12 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("/Users/xiaoshu/logs/a.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        ByteBuffer byteBuffer = ByteBuffer.wrap("abcde".getBytes());
        Future<Integer> future = channel.write(byteBuffer, channel.size());
        System.out.println("length = " + future.get());
        channel.close();
    }
}
