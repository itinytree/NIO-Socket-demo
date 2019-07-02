package me.qianlv.bytebuffer;

import java.nio.ByteBuffer;

/**
 * 非直接缓冲区的运行效率
 *
 * @author xiaoshu
 */
public class Test1_2 {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1900000000);
        for (int i = 0; i < 1900000000; i++) {
            byteBuffer.put((byte) 123);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
    }
}
