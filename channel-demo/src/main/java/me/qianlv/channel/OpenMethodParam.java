package me.qianlv.channel;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * OpenOption选项SYNC同步性能测试
 *
 * @author xiaoshu
 */
public class OpenMethodParam {
    public static void main(String[] args) throws IOException {
        testSync();
    }

    public static void test() throws IOException {
        File file = new File("/Users/xiaoshu/logs/f.txt");
        Path path = file.toPath();
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            fileChannel.write(ByteBuffer.wrap("a".getBytes()));
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        fileChannel.close();
    }

    public static void testSync() throws IOException {
        File file = new File("/Users/xiaoshu/logs/f.txt");
        Path path = file.toPath();
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE, StandardOpenOption.SYNC);
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 200; i++) {
            fileChannel.write(ByteBuffer.wrap("a".getBytes()));
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        fileChannel.close();
    }
}
