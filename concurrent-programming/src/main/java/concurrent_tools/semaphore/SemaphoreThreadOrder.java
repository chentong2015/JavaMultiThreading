package concurrent_tools.semaphore;

import java.util.concurrent.Semaphore;

// TODO. 通过Semaphore信号量控制线程的执行顺序
public class SemaphoreThreadOrder {

    // 初始化不设置Permit许可
    private static Semaphore semaphore1 = new Semaphore(0);
    private static Semaphore semaphore2 = new Semaphore(0);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("Thread 1 finish - release semaphore1");
            semaphore1.release();
        });

        Thread thread2 = new Thread(() -> {
            try {
                semaphore1.acquire(); // 只有等线程1执行完后释放Permit才能执行
                System.out.println("Thread 2 finish - release semaphore2");
                semaphore2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                semaphore2.acquire(); // 只有等线程2执行完后释放Permit才能执行
                System.out.println("Thread 3 finish");
                semaphore2.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread3.start();
        thread2.start();
        thread1.start();
    }
}
