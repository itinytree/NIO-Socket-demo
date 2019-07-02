package me.qianlv.bytebuffer;

import java.nio.ByteBuffer;

/**
 * 直接缓冲区的运行效率
 *
 * @author xiaoshu
 */
public class Test1_1 {
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1900000000);
        for (int i = 0; i < 1900000000; i++) {
            byteBuffer.put((byte) 123);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
    }
}
