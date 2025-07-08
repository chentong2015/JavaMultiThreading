package task_pool.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    // 需要在最上层将所有的query整合在一起，全部添加到task list中
    // 批量提交，然后批量等待结束，最后将Long的结果设置到Object中
    //
    // execution service
    //   validation service
    //      jpa repository
    public static void main(String[] args) {
        ConcurrencyTaskPool<String> futureUtil = new ConcurrencyTaskPool<>(4);

        // 将要执行的task全部收集到list中，然后提交执行
        List<IdentifiedTask<String>> tasks = Arrays.asList(
                new IdentifiedTask<>("taskA", () -> {
                    Thread.sleep(10000);
                    return "Result A";
                }),
                new IdentifiedTask<>("taskB", () -> {
                    Thread.sleep(5000);
                    return "Result B";
                }),
                new IdentifiedTask<>("taskC", () -> {
                    Thread.sleep(3000);
                    return "Result C";
                })
        );

        futureUtil.runAsyncTasksAndWait(tasks);
        Map<String, String> results = futureUtil.getTaskResults();

                // 获取指定任务的结果
        System.out.println("taskB result = " + results.get("taskB"));
        System.out.println("taskB result = " + results.get("taskC"));

        futureUtil.shutdown();
    }

}
