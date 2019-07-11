package me.qianlv.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 验证：将位置设置为大于文件当前大小的值是合法的，单这不会更改文件的大小，试图在这样的位置读取字节将立即返回已到达文件末尾的指示，
 * 试图在这种位置写入字节将导致文件扩大，以容纳新的字节，在以前文件末尾和新写入字节之间的字节值是未指定的
 * <p>
 * c.txt默认文件内容 abcde
 *
 * @author xiaoshu
 */
public class Test9 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/xiaoshu/logs/c.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        System.out.println("A position=" + fileChannel.position() + " size=" + fileChannel.size());
        System.out.println(fileChannel.read(ByteBuffer.allocate(10), 100000));
        System.out.println("B position=" + fileChannel.position() + " size=" + fileChannel.size());
        fileChannel.position(9);
        System.out.println("C position=" + fileChannel.position() + " size=" + fileChannel.size());
        fileChannel.write(ByteBuffer.wrap("z".getBytes()));
        System.out.println("D position=" + fileChannel.position() + " size=" + fileChannel.size());
        fileChannel.close();
        randomAccessFile.close();
    }
}