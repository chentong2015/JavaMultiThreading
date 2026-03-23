package threadpool;

import java.util.concurrent.*;

// TODO. Executors: 线程池工具类，封装线程池的初始化
// newCachedThreadPool();    有多少任务就会创建多少线程，创建和调度线程耗CPU100%，不造成OOM
// newFixedThreadPool(3);    任务增多，积累到阻塞队列中，内存无限增多，造成OOM
// newSingleThreadExecutor() 只有一个线程，多任务积累到阻塞队列，造成OOM
public class ThreadPoolExecutors {

    // TODO. 如果不设置shutdown()，线程池中的线程不会结束，程序不会结束 !!
    //  - Shutdown orderly for previously submitted tasks, no new tasks will be accepted.
    //  - Invocation has no additional effect if already shut down.
    //  - Does not wait for previously submitted tasks to complete.
    public static void main(String[] args) throws InterruptedException {
        String[] list = {"item01", "item02", "item03", "item04"};
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                for (String item : list) {
                    System.out.println(item);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                System.out.println("error");
            }
        });

        // TODO. shutdown()不会阻塞主线程
        // 确保线程池中所有的线程结束, 不在接受新的task执行任务, 并且不会再次复用
        // 如果不调用.shutdown(), 虚拟机可能不会退出 (因为还有线程池中线程存活)
        executorService.shutdown();

        // Attempts to stop all actively executing tasks
        // 正在执行的线程被中断，安全退出，不接收新的任务也不会执行阻塞队列中的任务
        executorService.shutdownNow();

        // Returns true if all tasks have completed following shut down.
        // Note that isTerminated is never true unless either shutdown or shutdownNow was called first.
        while (!executorService.isTerminated()) {
            // 判断线程池中所有task全部执行结束
        }

        // Blocks until all tasks have completed execution after a shutdown request,
        // or the timeout occurs, or the current thread is interrupted
        boolean isCompleted = executorService.awaitTermination(5, TimeUnit.SECONDS);
    }

    public void awaitTerminationAfterShutdown(ExecutorService threadPool) {
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
