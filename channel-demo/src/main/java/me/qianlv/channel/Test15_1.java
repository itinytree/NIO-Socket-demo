package me.qianlv.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * void force(boolean metaData)方法的性能
 *
 * @author xiaoshu
 */
public class Test15_1 {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/xiaoshu/logs/d.txt");
        if (!file.exists()) {
            file.createNewFile();
        } else {
            file.delete();
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        FileChannel channel = outputStream.getChannel();
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            channel.write(ByteBuffer.wrap("abcde".getBytes()));
            channel.force(false);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        channel.close();
        outputStream.close();
    }
}
