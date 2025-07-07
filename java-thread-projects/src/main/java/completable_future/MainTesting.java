package completable_future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class MainTesting {

    // 将
    public static void main(String[] args) {
        CompletableFutureHelper helper = new CompletableFutureHelper(4);
        List<Callable<String>> tasks = Arrays.asList(
                () -> {
                    Thread.sleep(10000);
                    return "Task 1";
                },
                () -> {
                    Thread.sleep(8000);
                    return "Task 2";
                },
                () -> {
                    Thread.sleep(5000);
                    return "Task 3";
                }
        );

        try {
            List<String> results = helper.runTasksAndWait(tasks);
            System.out.println("All tasks completed:");
            results.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Error during task execution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            helper.shutdown();
        }
    }
}
