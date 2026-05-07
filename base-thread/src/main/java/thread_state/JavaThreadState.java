package thread_state;

import java.util.concurrent.locks.LockSupport;

// TODO. 六种线程状态: 和运行时监控的状态一致
public class JavaThreadState {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start main thread");
        System.out.println(Thread.currentThread().getState()); // RUNNABLE

        // testRun();
        // testTimeWaiting();
        testWaiting();
    }

    private static void testRun() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("run thread 1");
        });
        System.out.println(thread.getState()); // NEW

        thread.start();
        System.out.println(Thread.currentThread().getState()); // RUNNABLE

        thread.join();
        System.out.println(thread.getState()); // TERMINATED
        System.out.println("Done thread 1");
    }

    private static void testTimeWaiting() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("run thread 2");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        Thread.sleep(1000);
        System.out.println(thread.getState()); // TIME_WAITING 有限期等待
    }

    private static void testWaiting() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("run thread 3");
            LockSupport.park(); // Disables the current thread
        });
        thread.start();

        Thread.sleep(1000);
        System.out.println(thread.getState()); // WAITING 无限期等待，直到唤醒或中断

        LockSupport.unpark(thread);

        Thread.sleep(1000);
        System.out.println(thread.getState());
    }
}
