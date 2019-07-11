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
 * 文件内容
 * abcdefg
 *
 * @author xiaoshu
 */
public class Test16 {
    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("/Users/xiaoshu/logs/e.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 5);
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println();
        buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 2, 2);
        //缓冲区的第0个位置是c
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        System.out.println((char) buffer.get() + " position=" + buffer.position());

        TimeUnit.SECONDS.sleep(1);
        System.out.println();
        //下面代码出现异常，因为超出映射的范围
        System.out.println((char) buffer.get() + " position=" + buffer.position());
        fileChannel.close();
        randomAccessFile.close();
    }
}
