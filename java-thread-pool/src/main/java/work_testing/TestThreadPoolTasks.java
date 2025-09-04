package work_testing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// TODO. 并发任务场景:
// 从主线程新开一个异步线程，和主线程并发执行(执行的逻辑相互独立)
// 确保两个线程执行结束，获取各自线程的返回结果
public class TestThreadPoolTasks {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Long> countFuture = executor.submit(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(15000);
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

        // 在主线程的task执行完之后，再等待6s的时间
        // 如果在timeout的时间内子线程的task还没有结束，则报错
        try {
            Long count = countFuture.get(6, TimeUnit.SECONDS);
            System.out.println("count = " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
