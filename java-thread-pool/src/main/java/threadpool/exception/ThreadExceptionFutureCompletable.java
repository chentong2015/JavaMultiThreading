package threadpool.exception;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExceptionFutureCompletable {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // TODO. 控制异步线程抛出异常时的处理, 返回future的特定值或将异常抛到外部
        CompletableFuture<Void> future = CompletableFuture
            .runAsync(ThreadExceptionFutureCompletable::process, executorService)
            .exceptionally(ex -> {
                System.out.println("Catch::" + ex.getMessage());
                throw new RuntimeException("inside exception");
            });

        try {
            System.out.println(future.join());
        } catch (Exception exception) {
            System.out.println("Main Catch::" + exception.getMessage());
        }
        executorService.shutdown();
    }

    private static void process() {
        System.out.println("Process items");
        throw new RuntimeException("Items exception");
    }
}
