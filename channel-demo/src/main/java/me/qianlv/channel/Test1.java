package me.qianlv.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

/**
 * 验证int write(ByteBuffer src)方法具有同步性
 *
 * @author xiaoshu
 */
public class Test1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileOutputStream outputStream = new FileOutputStream(new File("/Users/xiaoshu/logs/a.txt"));
        FileChannel fileChannel = outputStream.getChannel();
        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(() -> {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.wrap("abcde\r\n".getBytes());
                    fileChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Thread thread2 = new Thread(() -> {
                try {
                    ByteBuffer byteBuffer = ByteBuffer.wrap("我是中国人\r\n".getBytes());
                    fileChannel.write(byteBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread1.start();
            thread2.start();
        }

        TimeUnit.SECONDS.sleep(3);
        fileChannel.close();
        outputStream.close();
    }
}
