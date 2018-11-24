package count_utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author WilderGao
 * time 2018-11-24 11:24
 * motto : everything is no in vain
 * description 多线程技术工具 CyclicBarrier 的应用
 */
public class CyclicBarrierDemo {
    private static final int THREAD_COUNT_NUM = 7;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT_NUM, () -> {
            System.out.println("七个法师已经召唤完毕...开始寻找龙珠");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            collectDragon();
        });

        for (int i = 1; i <= THREAD_COUNT_NUM; i++) {
            int index = i;
            new Thread(()->{
                System.out.println("第 "+index+"个法师正在骑马赶来的路上......");
                try {
                    cyclicBarrier.await();
                    Thread.sleep(1000);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                    //如果出现异常就进行重置，将屏障设置为初始状态
                    cyclicBarrier.reset();
                }
            }).start();
        }
    }

    private static void collectDragon(){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT_NUM,() -> System.out.println("龙珠已经收集完毕......"));
        for (int i = 1; i <= THREAD_COUNT_NUM; i++) {
            int index = i;
            new Thread(() -> {
                System.out.println("已经找到第 "+index+ " 颗龙珠......");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
