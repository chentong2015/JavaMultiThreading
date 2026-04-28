package threadpool.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// 提交任务时submit()会把异常封装进Future, get()时再抛出来
public class ThreadPoolExceptionFuture {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            System.out.println("Inside Thread message");
            throw new RuntimeException("Inside exception !");
        });

        try {
            System.out.println(future.get()); // 外部线程捕获异常
        } catch (Exception exception) {
            System.out.println("Catch exception: " + exception.getMessage());
        }
        executorService.shutdown();
    }
}
