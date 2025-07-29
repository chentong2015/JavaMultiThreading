package multitask_pool;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CompletableFutureHelper {

    private final ExecutorService executor;

    public CompletableFutureHelper(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    // 提交一批任务，异步执行，并等待所有任务完成后返回结果
    public <T> List<T> runTasksAndWait(List<Callable<T>> tasks) {
        // 将每个Callable包装成CompletableFuture
        List<CompletableFuture<T>> futures = tasks.stream()
                .map(task -> CompletableFuture.supplyAsync(() -> {
                    try {
                        return task.call();
                    } catch (Exception e) {
                        throw new CompletionException(e);  // 转换为非检查异常
                    }
                }, executor)).toList();

        // 等待所有任务完成
        CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        allDone.join();

        // 收集结果（如果有异常会抛出）
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // 关闭线程池中线程，释放资源
    public void shutdown() {
        executor.shutdown();
    }
}
