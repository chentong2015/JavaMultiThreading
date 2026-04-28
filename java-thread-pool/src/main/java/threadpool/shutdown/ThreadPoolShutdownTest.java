package threadpool.shutdown;

import java.util.concurrent.*;

// TODO. 调用shutdown关闭线程池, 不会阻塞main线程的执行
// - 调用shutdown()之后, 线程池将不再接受新的提交任务
// - 调用shutdown()之后, 会等待正在运行的任务和队列中的任务执行结束
public class ThreadPoolShutdownTest {

    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5,
            10,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5));

    public static void main(String[] args) throws InterruptedException {
        for (int index = 0; index < 50; index++) {
            if (index == 12) {
                System.out.println("Call shutdown");
                threadPoolExecutor.shutdown();
                // threadPoolExecutor.shutdownNow(); 立即停止
                // threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS); 阻塞主线程
                break;
            }

            final int finalIndex = index;
            threadPoolExecutor.execute(() -> {
                System.out.println("run thread " + finalIndex);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // 如果线程池调用shutdownNow(), 而此刻sleep并没有执行完毕
                    // 则立即抛出InterruptedException: sleep interrupted异常
                    throw new RuntimeException(e);
                }
            });
            Thread.sleep(500);
        }
        System.out.println("All finished");
    }
}
