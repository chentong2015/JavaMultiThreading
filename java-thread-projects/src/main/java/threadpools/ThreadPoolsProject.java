package threadpools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ThreadPoolsProject {


    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        System.out.println("Start application");

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int taskId = i;
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    long timeout = new Random().nextLong(1, 6);
                    Thread.sleep(timeout * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Task " + taskId + " completed";
            }, executor);

            futures.add(future);
        }

        System.out.println("add all futures");

        // 并发等待所有任务完成
        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allDone.join();
        System.out.println("join");

        // 获取所有结果
        List<String> results = futures.stream().map(CompletableFuture::join).toList();
        results.forEach(System.out::println);

        System.out.println("done");

        // executor.shutdown();
    }
}
