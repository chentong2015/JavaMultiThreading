package project_tasklimiter.custom;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

public class CustomThrottleLimiter {

    // 信号量: 设置初始值, 只允许指定数量的Task并发运行
    private final Semaphore semaphore;

    // 默认最小堆Min-Heap, 需要使用最大堆排序
    private final BlockingQueue<CustomTask> blockingTaskQueue;

    public CustomThrottleLimiter(int throttleLimit) {
        // 定义Task优先级排序的规则
        this.blockingTaskQueue = new PriorityBlockingQueue<>(throttleLimit, new Comparator<CustomTask>() {
            @Override
            public int compare(CustomTask task1, CustomTask task2) {
                return task1.getPriority() - task2.getPriority();
            }
        });
        this.semaphore = new Semaphore(throttleLimit);
    }

    public synchronized void putTask(CustomTask task) {
        this.blockingTaskQueue.add(task);
    }

    // 使用while循环, 判断是否还有任务需要执行
    public void runTask() throws InterruptedException {
        while (true) {
            this.semaphore.acquire();
            if (this.blockingTaskQueue.isEmpty()) {
                this.semaphore.release();
                break;
            }

            // TODO. 每次执行任务都需要创建新线程(开销很大)，无法重用
            CustomTask customTask = this.blockingTaskQueue.take();
            new Thread(() -> {
               try {
                   customTask.run(); // 同步线程级别调用, 执行任务
               } finally {
                   this.semaphore.release(); // 确保异步线程能够释放Permit
               }
            }).start();
        }
    }
}
