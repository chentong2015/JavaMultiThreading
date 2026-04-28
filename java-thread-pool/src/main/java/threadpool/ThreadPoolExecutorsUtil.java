package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// TODO. Executors: 线程池工具类，封装线程池的初始化
// newCachedThreadPool();    有多少任务就会创建多少线程，创建和调度线程耗CPU100%，不造成OOM
// newFixedThreadPool(3);    任务增多会追加到阻塞队列中，内存无限增多，造成OOM
// newSingleThreadExecutor() 只有一个线程，多任务积累到阻塞队列，造成OOM
public class ThreadPoolExecutorsUtil {

    public static void main(String[] args) {
        String[] list = {"item01", "item02", "item03", "item04"};
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                for (String item : list) {
                    System.out.println(item);
                    Thread.sleep(3000);
                }
            } catch (InterruptedException e) {
                System.out.println("error");
            }
        });
        executorService.shutdown();
    }
}
