package me.qianlv;

import java.nio.ByteBuffer;
import java.nio.InvalidMarkException;

/**
 * @author xiaoshu
 * <p>
 * clear(): 还原缓冲区到初始化的状态
 */
public class Test8 {
    public static void main(String[] args) {
        byte[] byteArray = new byte[]{1, 2, 3};
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        System.out.println("byteBuffer.position=" + byteBuffer.position() + " byteBuffer.capacity=" + byteBuffer.capacity() + " byteBuffer.limit=" + byteBuffer.limit());
        byteBuffer.position(1);
        byteBuffer.limit(2);
        byteBuffer.mark();
        System.out.println("byteBuffer.position=" + byteBuffer.position() + " byteBuffer.capacity=" + byteBuffer.capacity() + " byteBuffer.limit=" + byteBuffer.limit());
        byteBuffer.clear();
        System.out.println("byteBuffer.position=" + byteBuffer.position() + " byteBuffer.capacity=" + byteBuffer.capacity() + " byteBuffer.limit=" + byteBuffer.limit());
        try {
            byteBuffer.reset();
        } catch (InvalidMarkException e) {
            System.out.println("byteBuffer mark丢失");
        }
    }
}
