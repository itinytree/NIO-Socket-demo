package me.qianlv.buffer;

import java.nio.ByteBuffer;

/**
 * arrayOffset()
 *
 * @author xiaoshu
 */
public class Test15 {
    public static void main(String[] args) {
        byte[] byteArray = new byte[]{1, 2, 3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        System.out.println("bytebuffer.arrayOffset=" + byteBuffer.arrayOffset());
    }
}
