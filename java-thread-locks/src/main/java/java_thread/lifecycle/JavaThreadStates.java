package java_thread.lifecycle;

// TODO. 六种线程状态: 和运行时监控的状态一致
public class JavaThreadStates {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start main thread");
        System.out.println(Thread.currentThread().getState()); // RUNNABLE

        Thread thread = new Thread(() -> {
            System.out.println("run thread 1");
            System.out.println(Thread.currentThread().getState()); // RUNNABLE
        });
        System.out.println(thread.getState()); // NEW

        thread.start();
        System.out.println("Done thread 1");

        // Waits for this thread to die
        thread.join();
        System.out.println(thread.getState()); // TERMINATED
    }
}
