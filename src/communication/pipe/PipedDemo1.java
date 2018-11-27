package communication.pipe;


import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author Wilder Gao
 * time:2018/1/25
 * Description：Piped类型的流，必须要先进行绑定，也就是调用connect()方法，
 * 如果没有将输入流和输出流绑定起来将会出现异常
 */
public class PipedDemo1 {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in),"PrintThread");
        printThread.start();
        int receive = 0;
        while ((receive = System.in.read())!= -1){
            out.write(receive);
        }
        out.close();
    }

    static class Print implements Runnable{
        private PipedReader in;
        public Print(PipedReader in){
            this.in = in ;
        }
        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read())!= -1){
                    System.out.print((char)receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}