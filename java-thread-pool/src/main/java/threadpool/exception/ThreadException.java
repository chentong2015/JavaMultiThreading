package threadpool.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadException {

    public static void main(String[] args) {
        AtomicBoolean hasError = new AtomicBoolean(false);
        ExecutorService taskExecutor = Executors.newFixedThreadPool(4);
        try {
            int index = 0;
            while (!hasError.get()) {
                int finalIndex = index;

                // TODO. 线程池中子线程抛出的异常不会传播到主线程
                taskExecutor.execute(() -> {
                    try {
                        processItems(finalIndex);
                    } catch (Exception exception) {
                        System.out.println("Find exception inside thread");
                        // 通过标识位来控制外层循环的结束
                        hasError.set(true);
                        throw exception;
                    }
                });
                index++;
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            // 无法捕获线程池内部抛出的异常 !!
            System.out.println("Catch::" + e.getMessage());
        } finally {
            System.out.println("clean up resources");
            taskExecutor.shutdown();
        }
    }

    private static void processItems(int finalIndex) {
        if (finalIndex == 3) {
            throw new RuntimeException("Error inside thread");
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
