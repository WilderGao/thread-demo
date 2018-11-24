package thread_pool.demo2;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * @author WilderGao
 * time 2018-11-24 15:00
 * motto : everything is no in vain
 * description
 */
public class MyCallable implements Callable<Integer> {
    private int num ;
    public MyCallable(int num){
        this.num = num;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("线程 "+ num +" 正在执行......");
        return num;
    }
}
