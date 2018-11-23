package communication;

import java.util.concurrent.TimeUnit;

/**
 * @author:Wilder Gao
 * @time:2018/1/25
 * @Discription：Thread.join()的使用
 * 程序功能：每个程序都包含着上一个线程，调用了join方法，只有等上一个线程结束的时候，下一个线程才会开始进行
 * 所以说，当main线程运行完毕之后，从join方法中返回，之后的线程会按照顺序执行
 */
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread previous = Thread.currentThread();
        for (int i = 0 ; i<10 ; i++){
            Thread thread = new Thread(new Domino(previous),String.valueOf(i));
            thread.start();
            previous = thread;
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+" terminate.");
    }

    static class Domino implements Runnable{
        private Thread thread;
        public Domino(Thread thread){
            this.thread = thread;
        }
        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" terminate.");
        }
    }
}
