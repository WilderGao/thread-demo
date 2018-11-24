## Summary of Thread
### Java中线程间的通信
- join方法  
 当线程A和B同时执行，线程A调用join方法之后，线程B需要等待线程A全部执行完之后才能够继续执行下去。
- wait 和 notify/notifyAll 方法  
    - 这两个方法并不是在 Thread 类中，而是在所有类的父类Object中。  
    - 调用wait方法可以使线程进入阻塞状态，只有只有等待别的线程唤醒或者被干扰 ( interrupt 方法) 才会进行下去或者抛出异常。  
    - 调用notify方法会随机唤醒处于等待中的线程，notifyAll方法唤醒所有等待的线程   
 **注意**：
        -  使用wait和notify方法时要先确保得到了该对象的锁。
        -  调用wait方法会释放锁，而调用notify时并不会释放锁，只有等获得锁的线程执行完方法之后才会将锁释放。
  
- 通过Lock的Condition对象进行通信  
Condition按字面意思理解就是条件，当然，我们也可以将其认为是条件进行使用，这样的话我们可以通过上述的代码创建多个Condition条件，
我们就可以根据不同的条件来控制现成的等待和通知。而我们还知道，在使用关键字synchronized与wait()方法和notify()方式结合实现线
程间通信的时候，notify/notifyAll的通知等待的线程时是随机的，显然使用Condition相对灵活很多，可以实现”选择性通知”。  

    这是因为，synchronized关键字相当于整个Lock对象只有一个单一的Condition对象，所有的线程都注册到这个对象上。线程开始notifAll
的时候，需要通知所有等待的线程，让他们开始竞争获得锁对象，没有选择权，这种方式相对于Condition条件的方式在效率上肯定Condition较高一些。

--代码详见 /communication/wait_notify

### ReentrantLock 和 ReentrantReadWriteLock   
- ReentrantLock 调用lock方法时会进行同步，也就是说无论是读还是写，每一次只有一个线程可以获得锁
  
- ReentrantReadWriteLock 分为读锁和写锁，多个线程可以同时获取读锁读取共享变量，而读与写、写与写则是互斥的，同一个时间内只能有一个线程可以获取到锁

### 线程池的创建
通过Executors框架可以创建出多种不同类型的线程池，我们一起来看一下：
```java
public class ThreadPoolDemo{
    public static void main(String[] args){
        // 创建只有一条线成的线程池，我们可以看到关键字SingleThread
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        //创建存在缓冲区的线程池，应用中可以存在的线程为无限多
        ExecutorService cachedThreadExecutor = Executors.newCachedThreadPool();
        //创建指定线程数量的线程池，参数代表线程的数量
        ExecutorService fixThreadExecutor = Executors.newFixedThreadPool(5);
        //创建一个定长线程池，支持定时及周期性任务执行
        ScheduledExecutorService scheduleThreadExecutor = Executors.newScheduledThreadPool(3);
        //延迟三秒执行
        scheduleThreadExecutor.schedule(new ThreadForPools(1), 4, TimeUnit.SECONDS);
    }
}
```
通过Executors框架创建出的线程很方便，但是通过阿里巴巴编码规范来看，这样不好管理我们的线程池,所以我们可以自己创建线程池
```java
public class ThreadPoolDemo{
    public static void main(String[] args){
        ExecutorService threadPool = new ThreadPoolExecutor(
                10, 15, 0L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                (Executors).defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
```
**对于线程池提交任务有两个方法: submit 和 execute， 任务有两种形式： Runnable 和 Callable**
#### Runnable 和 Callable
相同点
- 两者都是接口
- 两者都可用来编写多线程程序
- 两者都需要调用Thread.start()启动线程

不同点
- 实现Callable接口的任务线程能返回执行结果；而实现Runnable接口的任务线程不能返回结果
- Callable接口的call()方法允许抛出异常；而Runnable接口的run()方法的异常只能在内部消化，不能继续上抛

#### submit 和 execute
- 通过这两个方法可以向线程池提交任务
- submit可以提交Runnable，也可以提交Callable；execute提交Runnable

### ThreadLocal 如何做到每一个线程维护一个变量的副本
在ThreadLocal类中有一个static声明的Map，用于存储每一个线程的变量副本，Map中元素的键为线程对象，而值对应线程的变量副本。
详细代码可见 /src/thread_local