package threadspool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// TODO. 并发任务场景:
// 从主线程新开一个异步线程，和主线程并发执行
// 确保两个线程执行结束，最后获取返回结果
public class TestThreadPoolTasks {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Long> countFuture = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Sub Thread finished");
            return 10L;
        });

        System.out.println("Run main thread");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Main Thread finished");

        try {
            Long count = countFuture.get(60, TimeUnit.SECONDS);
            System.out.println("count = " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
