package threadpool.complete_future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// TODO. 获取线程池中线程执行后的返回值
// 1. Future<T> submit(Runnable task, T result);
// 2. Future<T> submit(Callable<T> task);
public class ThreadPoolExecutorsFuture {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            System.out.println("Inside Thread message");
            throw new RuntimeException("Inside exception !");
        });

        try {
            // TODO. Get获取结果时自动捕获线程池中线程的异常, 外部可见
            System.out.println(future.get());
        } catch (Exception exception) {
            System.out.println("Catch exception: " + exception.getMessage());
        }
        executorService.shutdown();
    }

    // TODO: 获取返回值时会阻塞当前(main)线程, 不可在UI线程中使用
    // Waits if necessary for the computation to complete, and then retrieves its result.
    private static void testGetThreadBackValue() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> "Back value");
        try {
            System.out.println(future.get());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        executorService.shutdown();
    }

    public void testSendRequestAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> "back value");
        try {
            // 只会做一次判断，之后获取数据的时候，仍然会阻塞当前的线程
            if (future.isDone()) {
                // Wait until a response is received
                String responseJson = future.get();
                // Send http request
            }
        } catch (Exception exception) {
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
