package concurrent_tools.countdown_latch;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 使用倒计时来等待所有任务的执行，不考虑任务的返回结果
public class CountDownLatchTaskWait {

    ExecutorService taskExecutor = Executors.newFixedThreadPool(3);

    public void executeAndWaitAllTasks(List<Runnable> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(tasks.size());
        for (Runnable task : tasks) {
            taskExecutor.execute(() -> {
                try {
                    task.run();
                } finally {
                    latch.countDown();
                }
            });
        }
        System.out.println("Summited " + tasks.size());

        // TODO. 阻塞等待计数归零，所有任务结束
        try {
            latch.await();
            System.out.println("All done");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
