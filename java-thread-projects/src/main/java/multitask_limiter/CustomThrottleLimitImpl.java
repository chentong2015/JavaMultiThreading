package multitask_limiter;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

public class CustomThrottleLimitImpl {

    // Lock锁: 在添加Task之后实现一个Notify通知的效果
    private final Object lock = new Object();

    // 信号量: 设置初始值(允许多少并发)，只允许指定数量的Task并发运行
    private final Semaphore semaphore;
    private final BlockingQueue<CustomTaskRunnable> runnableQueueTasks;

    public CustomThrottleLimitImpl(int throttleLimit) {
        this.runnableQueueTasks = new PriorityBlockingQueue<>(throttleLimit, new TaskComparator());
        this.semaphore = new Semaphore(throttleLimit);
    }

    // Put线程完成新的添加后，Notify通知Take线程取任务来执行
    public void putTask(CustomTaskRunnable taskRunnable) throws InterruptedException {
        this.semaphore.acquire();
        System.out.println("Get Semaphore permit");

        synchronized (lock) {
            this.runnableQueueTasks.add(taskRunnable);
            lock.notifyAll();
        }
    }

    // Take的线程会由于队列为空而CAS阻塞，直到接到Notify通知
    public void runTask() throws InterruptedException {
        synchronized (lock) {
            while (this.runnableQueueTasks.isEmpty()) {
                lock.wait();
            }
            try {
                this.runnableQueueTasks.take().run();
            } finally {
                // 线程执行完毕释放Permit，唤醒Put线程继续添加Task
                System.out.println("Release Semaphore permit -------- ");
                this.semaphore.release();
            }
        }
    }

    private static class TaskComparator implements Comparator<CustomTaskRunnable> {
        @Override
        public int compare(CustomTaskRunnable o1, CustomTaskRunnable o2) {
            return 0;
        }
    }
}
