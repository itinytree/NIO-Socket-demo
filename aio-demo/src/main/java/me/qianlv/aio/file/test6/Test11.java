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
 * 读取数据方式1
 * public abstract Future<Integer> read(ByteBuffer dst, long position) 从给定的文件位置开始,从该通道将字节序列读入给定的缓冲区
 *
 * @author xiaoshu
 */
public class Test11 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Path path = Paths.get("/Users/xiaoshu/logs/a.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
        Future<Integer> future = channel.read(byteBuffer, 0);
        System.out.println("length = " + future.get());
        channel.close();
        byte[] byteArray = byteBuffer.array();
        for (int i = 0; i < byteArray.length; i++) {
            System.out.println((char) byteArray[i]);
        }
    }
}
