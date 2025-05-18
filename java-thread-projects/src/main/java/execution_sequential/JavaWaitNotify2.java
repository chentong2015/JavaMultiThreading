package execution_sequential;

import java.util.concurrent.atomic.AtomicInteger;

// TODO. 基于加锁阻塞线程，使用Wait Notify实现交替执行
public class JavaWaitNotify2 {

    // 借助加锁对象来实现线程间交替同步
    private static Object lock = new Object();
    private static AtomicInteger value = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(() -> {
            while (value.get() <= 100) {
                synchronized (lock) {
                    lock.notifyAll(); // 唤醒其他线程
                    System.out.println("Thread 1 - " +  value.getAndIncrement());

                    try {
                        lock.wait(); // 等待另一个线程的唤醒
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (value.get() <= 100) {
                synchronized (lock) {
                    lock.notifyAll();
                    System.out.println("Thread 2 - " +  value.getAndIncrement());

                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}
