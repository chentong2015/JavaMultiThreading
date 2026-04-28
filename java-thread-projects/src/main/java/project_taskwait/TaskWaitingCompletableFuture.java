package project_taskwait;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskWaitingCompletableFuture {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> "index 1", threadPool);
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> "index 2", threadPool);
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> "index 3", threadPool);

        // join阻塞, 并行等待所有任务执行结束
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);
        allFutures.join();

        String result1 = completableFuture1.join();
        String result2 = completableFuture1.join();
        String result3 = completableFuture1.join();
    }
}
