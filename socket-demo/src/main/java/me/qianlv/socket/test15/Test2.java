package me.qianlv.socket.test15;

import java.io.IOException;
import java.net.Socket;

/**
 * 客户端
 *
 * @author xiaoshu
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8088);
        try {
            int count = 0;
            for (; ; ) {
                socket.sendUrgentData(1);
                count++;
                System.out.println("执行了 " + count + "次嗅探");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("----------网络断开了!");
            socket.close();
        }
    }
}
