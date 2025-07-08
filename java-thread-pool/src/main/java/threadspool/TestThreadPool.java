package threadspool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int index = 0; index < 5; index++) {
            int finalIndex = index;
            executorService.execute(() -> {
                System.out.println("start thread :" + finalIndex);
                try {
                    int time = finalIndex * 1000;
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Stop thread :" + finalIndex);
            });
        }
        System.out.println("Waiting for ending");
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // 判断线程池中所有task全部执行结束
        }

        System.out.println("All Finish !");
    }
}
