package work_tasklimiter.project;

// TODO. 动态计算内存分配，控制并发和等待任务的数量，避免OOM
public class MultiTaskLimiter {

    private static final double MEMORY_SAFETY_FACTOR = 0.7; // 安全系数
    private static final double THREAD_OVERHEAD_MB = 1.0; // 每个线程的栈内存开销
    private static final double SYSTEM_RESERVE_MB = 512.0; // 为系统保留的内存

    private final long maxMemoryUsed;
    private static final double SAFETY_FACTOR = 0.6;

    // 设置最大允许的任务数量(包含缓存队列中带处理的数目)
    private static final int MAX_TASK_LIMIT = 512;

    // By default, set the maximum amount of memory that JVM will attempt to use
    public MultiTaskLimiter() {
        this(Runtime.getRuntime().maxMemory() / (1024 * 1024)); // in MB
    }

    // For testing purpose
    public MultiTaskLimiter(long maxMemoryUsed) {
        this.maxMemoryUsed = maxMemoryUsed;
    }

    /**
     * Calculate the max numbers of waiting tasks allowed in BlockingQueue without out of memory
     * All the tasks in BlockingQueue will be consumed by multi-threads
     *
     * @param objectSizeInMB size of single object in MB
     * @param numObjects num objects processed by one task
     * @return max number tasks in queue
     */
    public int getMaxNumTasks(double objectSizeInMB, int numObjects) {
        if (objectSizeInMB <= 0 || numObjects <= 0) {
            throw new RuntimeException("objectSize and numObjects should be greater than 0");
        }

        double objectSizePerTask = objectSizeInMB * numObjects;
        if (objectSizePerTask > maxMemoryUsed * SAFETY_FACTOR) {
            throw new RuntimeException("Too large object size per task");
        }

        int maxTaskNumbers = (int) (maxMemoryUsed * SAFETY_FACTOR / objectSizePerTask);
        if (maxTaskNumbers > MAX_TASK_LIMIT) {
            return MAX_TASK_LIMIT;
        } else {
            return Math.max(maxTaskNumbers, 1);
        }
    }
}