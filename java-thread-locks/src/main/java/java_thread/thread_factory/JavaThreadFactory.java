package java_thread.thread_factory;

import java.util.concurrent.ThreadFactory;

public class JavaThreadFactory {

    // TODO. 本质上是创建一个线程工厂，提供创建线程的自定义方式
    public void testThreadFactory() {
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
}
