import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

// 通过线程工厂来创建新的线程或线程池中的线程: 本质上是实现Runnable接口
public class JavaThreadFactory {

    public static void main(String[] args) {
        ExecutorService executorServiceWithFactory = Executors.newSingleThreadExecutor(new MyThreadFactory());
        executorServiceWithFactory.execute(() -> System.out.println("execute"));

        ThreadFactory myThreadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                // Create new thread by Runnable Impl
                return new Thread(runnable);
            }
        };
        myThreadFactory.newThread(() -> {
            System.out.println("Impl Runnable");
        }).start();
    }

    static class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = Executors.defaultThreadFactory().newThread(r);
            t.setName("Thread name");
            t.setDaemon(true);
            return t;
        }
    }
}
