package work_taskwait_future.future_completable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

// TODO. CompletableFuture.allOf()
// - 异步/函数式，面向异步编排(非阻塞)
// - 适合复杂的任务依赖关系，回调/链式处理(thenApply/thenCompose等)
public class CompletableFutureWaitTasks {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "Back value";
            }, executorService);
            futureList.add(future);
        }

        // TODO. thenApply() 并行任务结束后的自动链式处理 => 不阻塞Main线程
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
            .thenApply(v ->// 非阻塞，任务结束后直接获取
                futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.joining(":"))
            ).thenAccept(System.out::println);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main thread done");
        executorService.shutdown();
    }
}