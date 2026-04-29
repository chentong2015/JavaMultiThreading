package concurrent_tools.countdown_latch;

import java.util.concurrent.CountDownLatch;

// TODO. CountDownLatch 倒计时锁存器
// 允许一个或多个线程，等待其他一组线程完成操作，再继续执行
// 需要确保某一个线程在其他一个或者多个优先级线程完成之后，再执行
public class CountDownLatchDemo {

    private static CountDownLatch c1 = new CountDownLatch(1);
    private static CountDownLatch c2 = new CountDownLatch(1);

    public static void main(String[] args) {
        final Thread thread1 = new Thread(() -> {
            System.out.println("Finish thread 1");
            c1.countDown();
        });

        final Thread thread2 = new Thread(() -> {
            try {
                c1.await(); // 等待c1倒计时，计时为0则往下运行
                System.out.println("Finish thread 2");
                c2.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                c2.await();  // 等待c2倒计时，计时为0则往下运行
                System.out.println("Finish thread 3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
