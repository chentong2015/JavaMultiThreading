package multitask_wait.project;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MultiTasksFutureManagerTest {

    public static void main(String[] args) {
        MultiTasksFutureManager<String> tasksManager = new MultiTasksFutureManager<>(4);
        List<ConcurrencyTask<String>> tasks = Arrays.asList(
                new ConcurrencyTask<>("idA", () -> {
                    Thread.sleep(8000);
                    return "Result A";
                }),
                new ConcurrencyTask<>("idB", () -> {
                    Thread.sleep(5000);
                    return "Result B";
                }),
                new ConcurrencyTask<>("idC", () -> {
                    Thread.sleep(3000);
                    return "Result C";
                })
        );

        // 注入异步执行的任务
        tasksManager.injectTasksAsync(tasks);

        tasksManager.cancelTaskById("idB");

        // 批量等待全部任务的结果，阻塞主线程
        Map<String, String> results = tasksManager.getTaskResults();
        System.out.println(results);

        // shutdown之后将不能再接受任务
        tasksManager.shutdown();
    }
}
