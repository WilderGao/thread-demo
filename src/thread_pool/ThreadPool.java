package thread_pool;

/**
 * @author:Wilder Gao
 * @time:2018/1/28
 * @Discription：
 */
public interface ThreadPool <Job extends Runnable>{
        //执行一个job
        void execute(Job job);
        //关闭线程池
        void shutdown();
        //增加工作者闲线程
        void addWorkers(int num);
        //减少工作者线程
        void removeWorkers(int num);
        //得到正在等待执行的任务数量
        int getJobSize();

}
