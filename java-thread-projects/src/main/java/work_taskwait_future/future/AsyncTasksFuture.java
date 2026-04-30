package work_taskwait_future.future;

import java.util.concurrent.*;

// TODO. 获取submit()并发线程执行后的返回值
// 1. Future<T> submit(Runnable task, T result);
// 2. Future<T> submit(Callable<T> task);
public class AsyncTasksFuture {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            Thread.sleep(5000);
            return "Back value";
        });

        Thread.sleep(2500);

        // 选择中断任务的执行，如果正在运行则Interrupt
        future.cancel(true);

        if (future.isCancelled()) {
            System.out.println("Task has been cancelled");
        } else {
            // TODO. 调用Get获取结果会阻塞当前线程 => 带抛出的异常
            // Cancel取消之后无法获取到以取消任务的结果
            System.out.println(future.get());

            // 支持多次获取同一个future的结果
            System.out.println(future.get(6, TimeUnit.SECONDS));
            System.out.println(future.isDone());
        }

        System.out.println("Finish result");
        executorService.shutdown();
    }
}
