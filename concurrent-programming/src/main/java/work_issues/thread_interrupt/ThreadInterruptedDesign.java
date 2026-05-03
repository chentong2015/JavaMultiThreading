package work_issues.thread_interrupt;

// TODO. 捕获异常: 消费中断信号并清除中断状态Status
public class ThreadInterruptedDesign {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 验证中断标志被清除
                System.out.println(Thread.currentThread().isInterrupted());
                // TODO. 传递到上层，避免"中断信号丢失"
                Thread.currentThread().interrupt();
            }
        });
        thread.start();
        Thread.sleep(2000);

        thread.interrupt();
        Thread.sleep(1000);

        // 确保外层拿到的状态一定是线程的中断状态
        System.out.println("Outer: " + thread.isInterrupted());
    }

    // TODO. 如果线程处于wait()期间同时被notify()和interrupt() ?
    // interrupt优先级更高，必然会抛出InterruptedException
    // 1. interrupt先发生，wait不会正常返回，线程无法继续执行
    // 2. notify先发生，wait返回后会被中断，线程也无法重新竞争锁再正常执行
    //
    // synchronized (lock) {
    //    while (!condition) {
    //        try {
    //            lock.wait();
    //        } catch (InterruptedException e) {
    //            Thread.currentThread().interrupt();
    //            return;
    //        }
    //    }
    // }
}
