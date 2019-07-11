package me.qianlv.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer map(FileChannel.MapMode mode, long position, long size) 方法的使用
 * <p>
 * public final MappedByteBuffer force()
 *
 * @author xiaoshu
 */
public class Test16_2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        testForce();
    }

    public static void test() throws IOException {
        File file = new File("/Users/xiaoshu/logs/e.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 100);
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            buffer.put("a".getBytes());
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        fileChannel.close();
        randomAccessFile.close();
    }

    public static void testForce() throws IOException {
        File file = new File("/Users/xiaoshu/logs/e.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 100);
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            buffer.put("a".getBytes());
            buffer.force();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        fileChannel.close();
        randomAccessFile.close();
    }
}
