package thread_local;


import java.util.*;

/**
 * @author WilderGao
 * time 2018-11-24 21:52
 * motto : everything is no in vain
 * description ThreadLocal几个简单操作的实现
 */
public class MyThreadLocal<T> {
    private Map<Thread, T> valueMap = Collections.synchronizedMap(new HashMap<>());

    public void set(T value){
        valueMap.put(Thread.currentThread(), value);
    }

    public T get(){
        Thread currentThread = Thread.currentThread();
        T t = valueMap.get(currentThread);
        if (t==null && !valueMap.containsKey(currentThread)){
            t = initialVaule();
            valueMap.put(currentThread, t);
        }
        return t;
    }

    public void remove(){
        valueMap.remove(Thread.currentThread());
    }

    private T initialVaule(){return null;}

}
