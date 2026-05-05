import java.util.concurrent.*;

// TODO. ScheduledThreadPool 周期性执行定时任务的线程池
public class ScheduledThreadPoolTask {

    public static void main(String[] args) {
        ExecutorService service = Executors.newScheduledThreadPool(10);
        for (int index = 0; index < 100; index++) {
            // service.execute(new MyTask());
        }

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new MyTimedThread(), 0, 5, TimeUnit.SECONDS);
    }

    static class MyTimedThread extends Thread {
        @Override
        public void run() {
            System.out.println("invoke task.");
        }
    }
}
