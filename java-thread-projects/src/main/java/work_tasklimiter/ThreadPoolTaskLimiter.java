package work_tasklimiter;

import work_tasklimiter.custom.CustomTask;

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTaskLimiter {

    // TODO. 对提交的任务根据优先级排序
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            5,
            5,
            0L,
            TimeUnit.MILLISECONDS,
            new PriorityBlockingQueue<>(10, new Comparator<Runnable>() {
                @Override
                public int compare(Runnable r1, Runnable r2) {
                    CustomTask t1 = (CustomTask) r1;
                    CustomTask t2 = (CustomTask) r2;
                    return Integer.compare(t1.getPriority(), t2.getPriority());
                }
            })
    );

    public static void main(String[] args) {
        Random random = new Random();
        for (int index = 0; index < 12; index++) {
            String taskName = "index " + index;
            threadPoolExecutor.execute(new CustomTask(taskName, random.nextInt(1, 5)));
        }
        threadPoolExecutor.execute(new CustomTask("new name", random.nextInt(1, 5)));

        System.out.println("Finish all tasks");
        threadPoolExecutor.shutdown();
    }
}
