package threadpool.complete_future;

import java.util.concurrent.*;

// TODO. 获取线程池中线程执行后的返回值
// 1. Future<T> submit(Runnable task, T result);
// 2. Future<T> submit(Callable<T> task);
public class ThreadPoolFuture {

    // TODO. 直接Get获取返回结果会阻塞当前线程
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(5000);
            return "Back value";
        });

        // Waits if necessary for the computation to complete, and then retrieves its result.
        System.out.println(future.get());
        System.out.println(future.get(6, TimeUnit.SECONDS));

        System.out.println("Finish result");
        System.out.println(future.isDone());

        executorService.shutdown();
    }

    // TODO. Get获取结果时自动捕获线程池中线程的异常, 使得外部可见
    public static void main2(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            System.out.println("Inside Thread message");
            throw new RuntimeException("Inside exception !");
        });

        try {
            System.out.println(future.get());
        } catch (Exception exception) {
            System.out.println("Catch exception: " + exception.getMessage());
        }
        executorService.shutdown();
    }
}
