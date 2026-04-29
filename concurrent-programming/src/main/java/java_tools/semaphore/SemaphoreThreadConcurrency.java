package java_tools.semaphore;

import java.util.concurrent.Semaphore;

// TODO. 通过Semaphore控制最大并发线程数量(并发线程由线程池管理)
public class SemaphoreThreadConcurrency {

    private static final int MAX_MULTI_THREADS_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(MAX_MULTI_THREADS_COUNT);

        for (int index = 0; index < 100; index++) {
             semaphore.acquire(); // 确保最多只有10线程能并发运行
             new Thread(() -> {
                 try {
                     System.out.println("Run thread: " + Thread.currentThread().getName());
                     Thread.sleep(3000);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 } finally {
                     semaphore.release(); // 确保线程的任务执行完毕后释放锁
                 }
             }).start();
        }
    }
}
