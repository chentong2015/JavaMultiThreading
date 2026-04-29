package multitask_wait.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.concurrent.*;

// 将批量等待并发任务的操作封装到Manager管理器中
public class MultiTasksFutureManager<T> {

    private final ExecutorService executor;
    private final Map<String, CompletableFuture<T>> futureMap;

    // 支持最大异步处理的任务数量，避免任务的无限增长造成OOM
    private static final int MAX_SUPPORTED_TASKS = 1024;

    public MultiTasksFutureManager(int maxConcurrentTasks) {
        if (maxConcurrentTasks <= 0) {
           throw new RuntimeException("Concurrency task num must be greater than 0");
        }

        int maxPoolSize = Runtime.getRuntime().availableProcessors();
        int threadPoolSize = Math.min(maxConcurrentTasks, maxPoolSize);
        this.executor = Executors.newFixedThreadPool(threadPoolSize);

        // 使用ConcurrentHashMap保证并发注入场景的安全性
        this.futureMap = new ConcurrentHashMap<>();
    }

    // TODO. 注意设计的线程安全性
    public void injectTasksAsync(List<ConcurrencyTask<T>> concurrencyTasks) {
        if (concurrencyTasks == null) {
            return;
        }
        if (executor.isShutdown()) {
            throw new RuntimeException("Concurrency task manager has been shut down.");
        }

        for (ConcurrencyTask<T> task : concurrencyTasks) {
            if (futureMap.containsKey(task.id())) {
                throw new RuntimeException("Can not inject duplicated tasks id");
            }

            CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return task.task().call();
                } catch (Exception e) {
                    throw new RuntimeException("Query Task '" + task.id() + "' executed failed.");
                }
            }, executor);

            futureMap.put(task.id(), future);
            if (futureMap.size() == MAX_SUPPORTED_TASKS) {
                throw new RuntimeException("Inject too much concurrency tasks");
            }
        }
    }

    public Map<String, T> getTaskResults() {
        Map<String, T> resultMap = new LinkedHashMap<>();
        if (futureMap.isEmpty()) {
            return resultMap;
        }

        try {
            // TODO. 只能获取所有未被取消的任务的结果
            List<CompletableFuture<T>> notCancelledFutures = futureMap.values()
                    .stream()
                    .filter(completableFuture -> !completableFuture.isCancelled())
                    .toList();
            if (notCancelledFutures.isEmpty()) {
                return resultMap;
            }

            // 阻塞当前线程, 等待全部并发任务的完成 => 未被取消的任务
            CompletableFuture.allOf(notCancelledFutures.toArray(new CompletableFuture[0])).join();

            // 完成后再直接获取结果 => 未被取消的任务
            for (Map.Entry<String, CompletableFuture<T>> entry : futureMap.entrySet()) {
                if (!entry.getValue().isCancelled()) {
                    resultMap.put(entry.getKey(), entry.getValue().join());
                }
            }
            return resultMap;
        } finally {
            futureMap.clear();
        }
    }

    // 实现带超时获取的版本
    public Map<String, T> getAllTaskResults(long timeout, TimeUnit unit) throws TimeoutException {
        // wait results with timeout
        return new HashMap<>();
    }

    // 取消所有提交的任务，终止正在执行的Task
    public void cancelAllTasks() {
        futureMap.values().forEach(f -> f.cancel(true));
    }

    // 只取消特定ID的任务
    public void cancelTaskById(String taskId) {
        if (futureMap.containsKey(taskId)) {
            futureMap.get(taskId).cancel(true);
        }
    }

    // 由调用者决定何时关闭线程池，以便多次注入和等待
    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }

        // 在没有调用getTaskResults()方法时释放资源
        futureMap.clear();
    }

    public boolean isShutdown() {
        return executor.isShutdown();
    }
}
