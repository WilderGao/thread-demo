package communication.wait_notify;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wilder
 * @date 18-11-23 上午11:43
 * description 利用 wait 和 notify 进行线程通信
 */
public class WaitDemo {
    private static Object lock = new Object();
    private static List<Integer> list = new LinkedList<>();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(()->{
            try {
                synchronized (lock) {
                    if (list.size() != 5) {
                        System.out.println("wait begin:" + Thread.currentThread().getName());
                        lock.wait();
                        System.out.println("wait end:"+Thread.currentThread().getName());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        Thread.sleep(2000);
        Thread thread2 = new Thread(()->{
            try {
                synchronized (lock){
                    for (int i = 0; i < 10; i++) {
                        list.add(i);
                        if (list.size()==5) {
                            lock.notify();
                            System.out.println("notify:" + Thread.currentThread().getName());
                        }
                        System.out.println("add No."+i);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        thread2.start();
    }
}
