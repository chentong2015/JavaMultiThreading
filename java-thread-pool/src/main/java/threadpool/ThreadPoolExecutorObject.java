package threadpool;

import java.util.concurrent.*;

// TODO. ThreadPoolExecutor 线程池对象
// public ThreadPoolExecutor(
//    int corePoolSize,    核心线程数量
//    int maximumPoolSize, 最大线程数目: corePoolSize + 非核心线程
//    long keepAliveTime,  时间值: 非核心线程的生命周期, 活跃的等待时间
//    TimeUnit unit,       时间单位
//    BlockingQueue<Runnable> workQueue,  阻塞队列, 用于存放等待被执行的任务
//    ThreadFactory threadFactory,        由ThreadFactory创建线程
//    RejectedExecutionHandler handler) { 阻塞队列存放线程的拒绝策略
//       1. CallerRunsPolicy
//       2. AbortPolicy   抛异常，默认策略
//       3. DiscardPolicy 放弃任务，一旦被拒绝则放弃掉
//       4. DiscardOldestPolicy 放弃最老的任务
//       5. Impl RejectedExecutionHandler接口，自定义策略
//    ...
// }
public class ThreadPoolExecutorObject {

    // 最大数目的maxSize没有意义, 并发线程数受到内核线程限制
    public static ExecutorService newCachedThreadPool() {
        return new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>());
    }

    // 链表阻塞队列: 有界队列容量为Integer.MAX_VALUE, 足够容纳线程数目
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads,
                nThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    // TODO. 将coreSize和maxSize设置成相同数目能够防止线程池中线程"抖动"
    // 只有一个核心线程负责执行task, 其他的task全部放到阻塞线程队列中
    public static ExecutorService newSingleThreadExecutor() {
        return new ThreadPoolExecutor(1,
                1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        // return new FinalizableDelegatedExecutorService(executor);
    }
}
