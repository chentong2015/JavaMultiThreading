package multitask_wait.completable_future.project;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

// 将批量等待并发任务的操作封装到Manager管理器中
public class WaitTasksFutureManager<T> {

    private final ExecutorService executor;
    private final Map<String, CompletableFuture<T>> futureMap;

    public WaitTasksFutureManager(int maxNumTasks) {
        if (maxNumTasks <= 0) {
           throw new RuntimeException("Concurrency task num must be greater than 0");
        }

        int maxPoolSize = Runtime.getRuntime().availableProcessors();
        int threadPoolSize = Math.min(maxNumTasks, maxPoolSize);
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
        this.futureMap = new ConcurrentHashMap<>();
    }

    public void injectTasksAsync(List<ConcurrencyTask<T>> concurrencyTasks) {
        if (concurrencyTasks == null || concurrencyTasks.isEmpty()) {
            return;
        }
        if (executor.isShutdown()) {
            throw new RuntimeException("Concurrency task manager has been shut down.");
        }

        for (ConcurrencyTask<T> task : concurrencyTasks) {
            CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return task.task().call();
                } catch (Exception e) {
                    throw new RuntimeException("Query Task '" + task.id() + "' executed failed.");
                }
            }, executor);
            futureMap.put(task.id(), future);
        }
    }

    // TODO. 等待全部并发任务的完成，再获取返回数据 => 线程池将会被关闭，不能再注入
    public Map<String, T> getTaskResults() {
        Map<String, T> resultMap = new LinkedHashMap<>();
        if (futureMap.isEmpty()) {
            return resultMap;
        }
        try {
            CompletableFuture.allOf(futureMap.values().toArray(new CompletableFuture[0])).join();
            for (Map.Entry<String, CompletableFuture<T>> entry : futureMap.entrySet()) {
                resultMap.put(entry.getKey(), entry.getValue().join());
            }
            return resultMap;
        } finally {
            futureMap.clear();
            if (executor != null) {
                executor.shutdown();
            }
        }
    }
}
