package threadspool;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// TODO: 根据项目场景定义线程池的合理参数, 线程执行顺序受到OS的调度
public class ThreadPoolWorkflow {

    public static void main(String[] args) {
        // testThreadPoolExecutorSuccess();
        testThreadPoolExecutorFailed();
    }

    // 执行50个并发任务时，线程池中将有10个核心线程+5个非核心线程在工作
    // thread-pool.core-pool-size=10
    // thread-pool.max-pool-size=20
    // thread-pool.queue-capacity=35
    public static void testThreadPoolExecutorSuccess() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                20,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(35));
        for (int index = 0; index < 50; index++) {
            threadPoolExecutor.execute(new WorkThread());
        }
    }

    // 执行100个线程任务，则第31个线程任务会出现Rejected !
    // java.util.concurrent.RejectedExecutionException:
    // Task Thread[Thread-30,5,main] rejected from java.util.concurrent.ThreadPoolExecutor@15aeb7ab
    // [Running, pool size = 20, active threads = 20, queued tasks = 10, completed tasks = 0]
    //
    // - 后续的线程无法被执行，也无法放到阻塞队列
    // - 正在执行的20个线程并为执行完，没有多余的线程能接受新任务
    public static void testThreadPoolExecutorFailed() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                20,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10));
        for (int index = 0; index < 100; index++) {
            threadPoolExecutor.execute(new WorkThread());
        }
    }

    static class WorkThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                int duration = new Random().nextInt(5, 10);
                Thread.sleep(duration * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
