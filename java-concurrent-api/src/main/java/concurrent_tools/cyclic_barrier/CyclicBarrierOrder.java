package concurrent_tools.cyclic_barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// TODO. CyclicBarrier 循环屏障, 可反复使用
// 让一组线程到达一个屏障(同步点)时被阻塞，直到最后一个线程到达屏障时，所有被屏障拦截的任务才会运行
// 计数器设置为N, 那么凑齐第一批N个线程后，计数器归零，然后等下一批N线程
public class CyclicBarrierOrder {

    private static CyclicBarrier barrier1 = new CyclicBarrier(2);
    private static CyclicBarrier barrier2 = new CyclicBarrier(2);

    public static void main(String[] args) {
        final Thread thread1 = new Thread(() -> {
            try {
                System.out.println("Run Thread 1");
                barrier1.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        final Thread thread2 = new Thread(() -> {
            try {
                barrier1.await(); // 等到线程1执行完毕才能凑齐, 并执行
                System.out.println("Thread 1 done, run thread 2");
                barrier2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        final Thread thread3 = new Thread(() -> {
            try {
                barrier2.await(); // 等到线程2执行完毕才能凑齐, 并执行
                System.out.println("Thread 2 done, run thread 3");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        thread3.start();
        thread1.start();
        thread2.start();
    }
}
