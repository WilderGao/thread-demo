package thread_local;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WilderGao
 * time 2018-11-24 21:32
 * motto : everything is no in vain
 * description ThreadLocal的简单应用
 */
public class ThreadLocalDemo {
    private static ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) {
        new Thread(() -> {
            List<String> params = new ArrayList<>(4);
            params.add("I");
            params.add(" am");
            params.add(" wilder");
            threadLocal.set(params);
            System.out.println(Thread.currentThread().getName());
            threadLocal.get().forEach(s -> System.out.println(s));
            threadLocal.remove();
        }).start();

        new Thread(() -> {
            List<String> params = new ArrayList<>(4);
            params.add("You");
            params.add(" are");
            params.add(" purple");
            threadLocal.set(params);
            System.out.println(Thread.currentThread().getName());
            threadLocal.get().forEach(s -> System.out.println(s));
            threadLocal.remove();
        }).start();
    }

}
