package threadpool.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExceptionFuture {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 提交任务时submit()会把异常封装进Future
        Future<String> future = executorService.submit(() -> {
            System.out.println("Inside Thread message");
            throw new RuntimeException("Inside exception !");
        });

        // get()时外部线程捕获异常
        try {
            System.out.println(future.get());
        } catch (Exception exception) {
            System.out.println("Catch exception: " + exception.getMessage());
        }
        executorService.shutdown();
    }
}
