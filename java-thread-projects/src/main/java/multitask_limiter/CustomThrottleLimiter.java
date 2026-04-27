package multitask_limiter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

public class CustomThrottleLimiter {

    // 信号量: 设置初始值, 只允许指定数量的Task并发运行
    private final Semaphore semaphore;

    // 默认最小堆Min-Heap, 需要使用最大堆排序
    private final BlockingQueue<CustomTask> blockingTaskQueue;

    public CustomThrottleLimiter(int throttleLimit) {
        this.blockingTaskQueue = new PriorityBlockingQueue<>(throttleLimit, new CustomTaskComparator());
        this.semaphore = new Semaphore(throttleLimit);
    }

    public synchronized void putTask(CustomTask task) {
        this.blockingTaskQueue.add(task);
    }

    public void runTask() throws InterruptedException {
        while (true) {
            this.semaphore.acquire();
            System.out.println("Get Semaphore permit");
            if (this.blockingTaskQueue.isEmpty()) {
                this.semaphore.release();
                break;
            }

            CustomTask customTask = this.blockingTaskQueue.take();
            new Thread(() -> {
               try {
                   customTask.run(); // 同步线程级别调用, 执行任务
               } finally {
                   this.semaphore.release(); // 确保异步线程能够释放Permit
               }
            }).start(); // 新创建异步线程运行, 造成新线程开销 => 重用线程
        }
    }
}
