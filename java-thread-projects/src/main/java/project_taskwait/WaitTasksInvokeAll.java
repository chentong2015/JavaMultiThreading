package project_taskwait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WaitTasksInvokeAll {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<Long>> tasks = Arrays.asList(
                () -> {
                    Thread.sleep(1000);
                    System.out.println("Task1 done");
                    return 10L;
                },
                () -> {
                    Thread.sleep(2000);
                    System.out.println("Task2 done");
                    return 20L;
                },
                () -> {
                    Thread.sleep(1500);
                    System.out.println("Task3 done");
                    return 30L;
                }
        );

        // invokeAll提交并阻塞等待全部完成
        List<Future<Long>> futures = executor.invokeAll(tasks);

        long sum = 0;
        for (Future<Long> future : futures) {
            sum += future.get(); // 这里不会再阻塞(已经执行完)
        }
        System.out.println("Total sum = " + sum);

        executor.shutdown();
    }
}
