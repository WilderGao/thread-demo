package lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author:Wilder Gao
 * @time:2018/1/28
 * @Discription：
 */
public class TwinsLock implements Lock {
    private final Sync sync = new Sync(8);
    private static final class Sync extends AbstractQueuedLongSynchronizer {
        Sync(int count){
            if (count <= 0){
                throw new IllegalArgumentException("count must large than zero");
            }
            setState(count);
        }

        @Override
        protected long tryAcquireShared(long arg) {
            for (;;){
                int current = (int) getState();
                int newCount = (int) (current - arg);
                if (newCount < 0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(long arg) {
            for (;;){
                int current = (int) getState();
                int newCount = (int) (current+arg);
                if (compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }
    }
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {}

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}

