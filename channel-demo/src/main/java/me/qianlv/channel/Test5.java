package me.qianlv.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 验证long write(ByteBuffer[] srcs, int offset, int length)方法将ByteBuffer的remaining写入通道
 *
 * @author xiaoshu
 */
public class Test5 {
    public static void main(String[] args) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/xiaoshu/logs/b.txt"));
        FileChannel fileChannel = outputStream.getChannel();
        ByteBuffer byteBuffer1 = ByteBuffer.wrap("abcde".getBytes());
        ByteBuffer byteBuffer2 = ByteBuffer.wrap("12345".getBytes());
        byteBuffer2.position(1);
        byteBuffer2.limit(3);
        ByteBuffer byteBuffer3 = ByteBuffer.wrap("d1e1f1".getBytes());
        byteBuffer3.position(2);
        byteBuffer3.limit(4);
        ByteBuffer[] byteBufferArray = {byteBuffer1, byteBuffer2, byteBuffer3};
        fileChannel.write(byteBufferArray, 1, 2);
        fileChannel.close();
        fileChannel.close();
    }
}
