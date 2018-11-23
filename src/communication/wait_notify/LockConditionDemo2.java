package communication.wait_notify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WilderGao
 * time 2018-11-23 16:37
 * motto : everything is no in vain
 * description
 */
public class LockConditionDemo2 {
    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();

    private void await(Condition condition){
        try {
            lock.lock();
            System.out.println("开始等待await， ThreadName:"+Thread.currentThread().getName());
            condition.await();
            System.out.println("等待结束， ThreadName:"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void signal(Condition condition){
        lock.lock();
        System.out.println("开始发送通知， ThreadName:"+Thread.currentThread().getName());
        condition.signal();
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        LockConditionDemo2 conditionDemo = new LockConditionDemo2();
        new Thread(()-> conditionDemo.await(conditionDemo.conditionA), "thread1_conditionA").start();
        new Thread(()-> conditionDemo.await(conditionDemo.conditionB), "thread2_conditionB").start();
        new Thread(()-> conditionDemo.signal(conditionDemo.conditionA), "thread3_conditionA").start();

        System.out.println("稍等5秒再通知其他的线程");
        Thread.sleep(5000);
        new Thread(()-> conditionDemo.signal(conditionDemo.conditionB), "thread4_conditionB").start();
    }
}
