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
 