package threadpool.shutdown;

import java.util.concurrent.*;

// TODO. 线程池中的线程在执行完所有的任务后shutdown
// - 关闭线程池，使得其中的线程不再接受新的任务
// - 关闭线程池，主线程在执行结束后JVM才能终止
public class ThreadPoolShutdown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("error");
            }
        });

        // TODO. Does not wait 非阻塞形式关闭
        // 确保正在执行的任务结束, 线程不再接受新的执行
        executorService.shutdown();

        // 正在执行的线程被立即中断，退出且不再接收新的任务
        executorService.shutdownNow();

        // TODO. Blocks 阻塞形式关闭
        // 阻塞直到先前所有提交的任务都执行完毕或者Timeout
        executorService.shutdown();
        boolean isCompleted = executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(executorService.isTerminated());
    }

    // 封装线程池shutdown的方法
    public void awaitThreadPoolTermination(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(5, TimeUnit.MINUTES)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
