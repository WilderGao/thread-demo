package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:Wilder Gao
 * @time:2018/1/29
 * @Discription：Condition对象的实现事例
 */
public class ConditionUseCase {
    /**
     * 调用await()方法后，当前线程释放锁进入等待状态，
     * 只有当其它线程调用signal()方法之后，原来的线程才会从await()返回
     */
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void conditionWait(){
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void conditionSignal() throws InterruptedException{
        lock.lock();
        try {
            condition.signal();
        }finally {
            lock.unlock();
        }

    }

}
