package count_utils;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author WilderGao
 * time 2018-11-23 17:36
 * motto : everything is no in vain
 * description 多线程计数工具CountDownLatch 的应用
 */
public class CountDownLatchDemo {
    private static final int THREAD_COUNT_NUM = 7;
    private static CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT_NUM);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("开始收集龙珠");
        for (int i = 0; i < THREAD_COUNT_NUM; i++) {
            int index = i + 1;
            new Thread(() -> {
                System.out.println("第 " + index + " 颗龙珠已经找到");
                try {
                    Thread.sleep(new Random().nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }
        //等待栅栏数为0时才继续执行下面内容
        countDownLatch.await();
        System.out.println("龙珠已经收集完毕！！！");
    }
}
