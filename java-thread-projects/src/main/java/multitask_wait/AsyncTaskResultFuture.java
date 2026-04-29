package multitask_wait;

import java.util.concurrent.*;

// TODO. 获取并发线程执行后的返回值
// 1. Future<T> submit(Runnable task, T result);
// 2. Future<T> submit(Callable<T> task);
public class AsyncTaskResultFuture {

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
}
