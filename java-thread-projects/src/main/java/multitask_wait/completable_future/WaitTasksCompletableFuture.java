package multitask_wait.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaitTasksCompletableFuture {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // TODO. supplyAsync调用时异步线程立即执行
        CompletableFuture<String> cFuture1 = CompletableFuture.supplyAsync(() -> "index 1", threadPool);
        CompletableFuture<String> cFuture2 = CompletableFuture.supplyAsync(() -> "index 2", threadPool);
        CompletableFuture<String> cFuture3 = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("inside error");
        }, threadPool);

        try {
            // TODO. 创建一个组合Future(等待全部任务完成的逻辑) => 不阻塞Main线程
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(cFuture1, cFuture2, cFuture3)
                    .orTimeout(3, TimeUnit.MINUTES); // Timeout避免永久等待

            // TODO. 调用join时才会阻塞主线程 => 有异常则抛出异常
            allFutures.join();

            // 再获取结果时任务已经执行完毕
            System.out.println(cFuture1.join());
            System.out.println(cFuture2.join());
            System.out.println(cFuture3.join());
        } catch (Exception exception) {
            // 子线程抛出的异常能够被外层捕获
            System.out.println("Catch::" + exception.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }
}
