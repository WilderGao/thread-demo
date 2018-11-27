package communication.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author WilderGao
 * time 2018-11-27 22:02
 * motto : everything is no in vain
 * description 线程之间通过Piped进行通信demo
 * inputStream一定要和outputStream相互连接才会有效果
 */
public class PipedDemo2 {
    public static void main(String[] args) throws IOException {
        PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream();
        in.connect(out);

        ReadThread readThread = new ReadThread(in);
        WriteThread writeThread = new WriteThread(out);

        readThread.start();
        writeThread.start();
    }

}

class ReadThread extends Thread {
    private PipedInputStream inputStream;

    public ReadThread(PipedInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        int index = 0;
        byte[] bytes = new byte[1024];
        try {
            while ((index = inputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, index, StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class WriteThread extends Thread{
    private PipedOutputStream outputStream;

    public WriteThread(PipedOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        List<String> information = new LinkedList<>();
        information.add("I am Wilder!");
        information.add("I come from GuangDong!");
        information.add("I love badminton!");
        Iterator<String> iterator = information.iterator();
        try {
            while (iterator.hasNext()){
                String s = iterator.next();
                outputStream.write(s.getBytes());
                outputStream.flush();
                Thread.sleep(100);
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}