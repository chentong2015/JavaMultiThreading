package threadpool.complete_future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

// TODO. 使用CompletableFuture来批量获取线程池中线程执行结果
public class ThreadPoolFutureCompletable {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "Back value";
            }, executorService);
            futureList.add(future);
        }

        // TODO. 用allOf等全部完成 -> 用join取结果 -> joining拼接
        String result = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
                .orTimeout(1, TimeUnit.MINUTES)
                .thenApply(v -> futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.joining(":"))
                ).join();
        System.out.println(result);
        executorService.shutdown();
    }

    // TODO. 第一次join等待，第二次join直接取值(无阻塞)
    public void test(List<CompletableFuture<Long>> futuresCount) {
        CompletableFuture.allOf(futuresCount.toArray(new CompletableFuture[0]))
                .orTimeout(45, TimeUnit.MINUTES)
                .join();
        long totalCountDeleted = futuresCount.stream()
                .mapToLong(CompletableFuture::join)
                .sum();
        System.out.println(totalCountDeleted);
    }
}