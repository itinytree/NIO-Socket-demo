package me.qianlv;

import java.nio.ByteBuffer;

/**
 * @author xiaoshu
 * 直接缓冲区释放内存方法
 * 1. 手动释放
 * 2. 交给JVM处理
 * <p>
 * 此程序多次运行后,一直在耗费内存
 * 进程结束后,也不会马上回收内存
 * 而是会在某个时机触发GC垃圾回收器进行内存回收
 */
public class Test10 {
    public static void main(String[] args) {
        System.out.println("A");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Integer.MAX_VALUE);
        System.out.println("B");
        byte[] byteArray = new byte[]{1};
        System.out.println(Integer.MAX_VALUE);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            byteBuffer.put(byteArray);
        }
        System.out.println("put end.");
    }
}
