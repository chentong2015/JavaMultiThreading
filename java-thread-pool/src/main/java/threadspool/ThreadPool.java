package threadspool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    // TODO. 线程池避免大量线程的创建和销毁，提升应用的性能
    // 执行"大量短小并发(short-lived asynchronous tasks)"任务
    public static void main(String[] args) {
        Long startTime = System.currentTimeMillis();
        Random random = new Random();
        List<Integer> list = new ArrayList<>();

        // 创建2个线程: 一个主线程 + 一个线程池中的线程
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int index = 0; index < 1000; index++) {
            executorService.execute(
                () -> list.add(random.nextInt())
            );
        }
        executorService.shutdown();
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    // TODO. 不停的创建新线程, 创建和切换会造成性能低下
    private void testUsingThreads() throws InterruptedException {
        Long startTime = System.currentTimeMillis();
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        for (int index = 0; index < 1000; index++) {
            Thread thread = new Thread(() -> list.add(random.nextInt()));
            thread.start(); // 创建多线程，去调用线程的run()方法: 方法级别的调用 !!
            thread.join();  // 等着线程执行结束
        }
        for (int index = 0; index < 1000; index++) {
            // threads[index].join(); 等待线程数组中指定的线程结束
        }
        Long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }
}
