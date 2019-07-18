package me.qianlv.socket.test14;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @author xiaoshu
 */
public class Client14 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8088);
        OutputStream outputStream = socket.getOutputStream();
        //必须使用 OutputStreamWriter 类才能出现预期的效果
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        socket.sendUrgentData(97);
        outputStreamWriter.write("zzzzzzzzzzzzzzzzzzzzzzzzzzzzz!");
        socket.sendUrgentData(98);
        socket.sendUrgentData(99);
        //必须使用flush()， 不然不会出现预期效果
        outputStreamWriter.flush();
        socket.sendUrgentData(100);
        outputStream.close();
        socket.close();
    }
}
