package task_pool.demo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyTaskPool<T> {

    private final ExecutorService executor;
    private Map<String, CompletableFuture<T>> futureMap;

    public ConcurrencyTaskPool(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    // 提交任务并保留 ID 对应关系 // 等待全部完成
    public void runAsyncTasksAndWait(List<IdentifiedTask<T>> identifiedTasks) {
        futureMap = new LinkedHashMap<>();
        for (IdentifiedTask<T> it : identifiedTasks) {
            CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return it.getTask().call();
                } catch (Exception e) {
                    throw new CompletionException(e);
                }
            }, executor);
            futureMap.put(it.getId(), future);
        }
        CompletableFuture<Void> allDone = CompletableFuture.allOf(
            futureMap.values().toArray(new CompletableFuture[0])
        );
        allDone.join();
    }

    // 收集结果：id -> result
    public Map<String, T> getTaskResults() {
        Map<String, T> resultMap = new LinkedHashMap<>();
        for (Map.Entry<String, CompletableFuture<T>> entry : futureMap.entrySet()) {
            resultMap.put(entry.getKey(), entry.getValue().join());
        }
        return resultMap;
    }

    // 关闭线程池中线程，释放资源 ==> 一般在finally中执行
    public void shutdown() {
        executor.shutdown();
    }
}
