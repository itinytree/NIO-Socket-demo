package me.qianlv.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 验证int read(ByteBuffer dst)方法将字节放入ByteBuffer当前位置
 *
 * @author xiaoshu
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File("/Users/xiaoshu/logs/a.txt"));
        FileChannel fileChannel = inputStream.getChannel();
        fileChannel.position(2);
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        byteBuffer.position(3);
        //向ByteBuffer读入
        int readCount = fileChannel.read(byteBuffer);
        System.out.println("读取的字节数:" + readCount);
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            byte bc = byteBuffer.get();
            System.out.print(bc == 0 ? "空格" : (char) bc);
        }
        fileChannel.close();
        inputStream.close();
    }
}
