package me.qianlv.channel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * MappedByteBuffer map(FileChannel.MapMode mode, long position, long size) 方法的使用
 * <p>
 * 可读可写模式(READ_WRITE) 文件内容修改
 * 文件内容
 * abcdefg
 * <p>
 * 专用模式 PRIVATE 文件内容不修改
 *
 * @author xiaoshu
 */
public class Test16_1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("/Users/xiaoshu/logs/e.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println();
        buffer.position(0);
        buffer.put((byte) 'o');
        buffer.put((byte) 'p');
        buffer.put((byte) 'q');
        buffer.put((byte) 'r');
        buffer.put((byte) 's');
        fileChannel.close();
        randomAccessFile.close();
    }
}
