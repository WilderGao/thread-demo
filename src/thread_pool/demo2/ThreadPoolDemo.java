package thread_pool.demo2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author WilderGao
 * time 2018-11-24 12:16
 * motto : everything is no in vain
 * description 线程池使用demo
 */
public class ThreadPoolDemo {
    private static final int THREAD_NUM = 5;
    public static void main(String[] args) {
        // 在阿里巴巴编码规范中说到，最好手动创立线程池，参数可以由自己设置
        //不要使用Executors框架直接建立线程池
        ThreadFactory factory = new MyThreadFactory();
        ExecutorService executorService = new ThreadPoolExecutor(
              10, 15, 0L, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                factory, new ThreadPoolExecutor.AbortPolicy()
        );
        //使用 Future 来获取执行完的结果
        List<Future<Integer>> futures = new LinkedList<>();
        for (int i =1;i<= THREAD_NUM;i++){
            int index = i;
            MyCallable callable = new MyCallable(index);
            Future<Integer> future = executorService.submit(callable);
            futures.add(future);
        }

        //通过调用future的get方法可以获得返回结果
        futures.forEach(v-> {
            try {
                int num = v.get();
                System.out.println(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        //最后关闭线程池
        executorService.shutdown();
    }
}

class MyThreadFactory implements ThreadFactory{
    private final AtomicInteger num = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r) {
        int c = num.getAndIncrement();
        Thread thread = new Thread(r);
        thread.setName("thread_no."+c);
        System.out.println("Create new Thread, name:"+thread.getName());
        return thread;
    }
}
