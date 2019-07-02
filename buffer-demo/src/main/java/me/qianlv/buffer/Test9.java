package me.qianlv.buffer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaoshu
 * 直接缓冲区释放内存方法
 * 1. 手动释放
 * 2. 交给JVM处理
 * <p>
 * 此程序运行的效果就是1秒钟之后立即回收内存
 * 也就是回收"直接缓冲区"所占用的内存
 */
public class Test9 {
    public static void main(String[] args) throws InterruptedException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println("A");
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(Integer.MAX_VALUE);
        System.out.println("B");
        byte[] byteArray = new byte[]{1};
        System.out.println(Integer.MAX_VALUE);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            byteBuffer.put(byteArray);
        }
        System.out.println("put end.");
        TimeUnit.SECONDS.sleep(1);
        Method cleanerMethod = byteBuffer.getClass().getMethod("cleaner");
        cleanerMethod.setAccessible(true);
        Object returnValue = cleanerMethod.invoke(byteBuffer);
        Method cleanMethod = returnValue.getClass().getMethod("clean");
        cleanMethod.setAccessible(true);
        cleanMethod.invoke(returnValue);
    }
}
