package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author:Wilder Gao
 * @time:2018/1/29
 * @Discription：
 */
public class BoundedQueue<T> {
    private Object[] items;
    private int addIndex , removeIndex , count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notNull = lock.newCondition();

    public BoundedQueue(int size){
        items = new Object[size];
    }

    /**
     * 添加内容，当队列满时将阻塞加入，等待其它线程获取队列中内容使队列中有空时将其唤醒
     * @param t
     */
    public void add(T t){
        lock.lock();
        try {
            while (count == items.length) {
                notNull.await();
            }
            //等待完之后有人将它唤醒,进行下列的操作
            items[addIndex] = t;
            addIndex++;
            if (addIndex == items.length){addIndex = 0;}
            count++;
            notEmpty.signal();
        }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
            lock.unlock();
        }
    }

    /**
     * 从头部删除一个元素，如果数组为空，则删除线程进入等待状态，直到有新元素添加
     * @return
     */
    public T remove() {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.wait();
            }
            Object x = items[removeIndex];
            removeIndex++;
            if (removeIndex == items.length) {
                removeIndex = 0;
            }
            count--;
            notNull.signal();
            return (T) x;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }
}
