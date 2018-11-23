package communication.wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wilder
 * @date 18-11-23 下午3:04
 * description 使用锁的Condition来进行线程之间的通信
 * 使用condition的await和signal与调用对象的wait和notify方法一样，需要先获取锁才可以执行
 */
public class LockConditionDemo {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void await(){
        //获取锁
        try {
            lock.lock();
            System.out.println("开始等待await！ ThreadName: "+Thread.currentThread().getName());
            condition.await();
            System.out.println("等待await结束! ThreadName: "+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void signal(){
        //记住一定要先获得锁
        lock.lock();
        System.out.println("开始发送通知！ ThreadName： "+Thread.currentThread().getName());
        condition.signal();
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        LockConditionDemo conditionDemo = new LockConditionDemo();
        new Thread(()-> conditionDemo.await(), "thread1").start();
        Thread.sleep(3000);
        new Thread(()->conditionDemo.signal(), "thread2").start();
    }
}
