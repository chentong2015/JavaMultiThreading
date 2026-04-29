package multitask_wait.completable_future.project;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WaitTasksFutureManagerTest {

    public static void main(String[] args) {
        WaitTasksFutureManager<String> tasksManager = new WaitTasksFutureManager<>(4);
        List<ConcurrencyTask<String>> tasks = Arrays.asList(
                new ConcurrencyTask<>("idA", () -> {
                    Thread.sleep(800);
                    return "Result A";
                }),
                new ConcurrencyTask<>("idB", () -> {
                    Thread.sleep(500);
                    return "Result B";
                }),
                new ConcurrencyTask<>("idC", () -> {
                    Thread.sleep(300);
                    return "Result C";
                })
        );

        // 注入异步执行的任务
        tasksManager.injectTasksAsync(tasks);

        // 批量等待全部任务的结果，阻塞主线程
        Map<String, String> results = tasksManager.getTaskResults();
        System.out.println(results);
    }
}
