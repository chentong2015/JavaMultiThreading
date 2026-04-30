package work_taskwait_future.invokeall;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

// TODO. invokeAll() 线程池工具方法
// - 同步/命令式, 立即阻塞主线程
// - 应用于简单可靠的批处理/CPU任务/导出统计
// - 很难区分每个结果对于的具体任务, 缺少隐射关系
public class WaitTasksInvokeAll {

    public static void main(String[] args) {
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
                    throw new RuntimeException("test exception");
                },
                () -> {
                    Thread.sleep(1500);
                    System.out.println("Task3 done");
                    return 30L;
                }
        );

        try {
            // 提交全部任务并阻塞等待完成, 一旦调用立即阻塞Main线程
            List<Future<Long>> futures = executor.invokeAll(tasks, 1, TimeUnit.MINUTES);

            // 已经执行完不再阻塞 => 异常被包在Future.get()里
            long sum = 0;
            for (Future<Long> future : futures) {
                sum += future.get();
            }
            System.out.println("Total sum = " + sum);
        } catch (Exception exception) {
            // 子线程抛出的异常能够被外层捕获
            System.out.println("Catch:: " + exception.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
