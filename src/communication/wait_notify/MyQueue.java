package communication.wait_notify;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wilder
 * @date 18-11-23 下午12:20
 * description 用wait 和 notify方法实现一个阻塞队列
 */
public class MyQueue {
    //1、定义一个元素集合
    private final LinkedList<Object> list = new LinkedList<>();
    //2、定义一个计数器
    private final AtomicInteger count = new AtomicInteger(0);

    //3、制定边界
    private final int maxSize = 5;
    private final int minSize = 0;

    private static Object lock = new Object();

    /**
     * put 方法
     *
     * @param obj 添加的对象
     */
    public void put(Object obj) throws InterruptedException {
        synchronized (lock) {
            while (maxSize == count.get()) {
                System.out.println("队列已满，需要等待......");
                //达到最大值，需要进行等待
                lock.wait();
            }
            list.add(obj);
            //计数器增加
            count.getAndIncrement();
            System.out.println("元素 " + obj + "被增加");
            //唤醒另一个阻塞的线程
            lock.notify();
        }
    }

    public Object get() throws InterruptedException {
        Object tmp;
        synchronized (lock) {
            while (minSize ==count.get()){
                System.out.println("队列为空，无元素取出，等待......");
                lock.wait();
            }
            tmp = list.removeFirst();
            count.getAndDecrement();
            System.out.println("元素 "+tmp+"被消费");
            lock.notify();
        }
        return tmp;
    }


}
