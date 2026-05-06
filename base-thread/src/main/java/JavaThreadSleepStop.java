public class JavaThreadSleepStop {

    // TODO: 线程的Sleep休眠，调用底层的OS去将线程sleep相应时间
    //  sleep()期间不会释放掉线程所拥有的锁，造成并发和性能问题
    //  sleep()期间的线程可能被"中断"，抛出中断异常
    public static void main(String[] args) {
        try {
            // 如果线程没有被中断打扰，则在3S时间后自动唤醒
            Thread.sleep(3000);
            Thread.sleep(3000, 10);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupt");
        }
    }

    // TODO. 等待线程的结束join(), 同时控制线程结束的顺序
    // 等待一个线程执行彻底结束, 也在多线程中调用另一个线程的.join()方法
    // 应用场景：控制线程的结束，约束执行的顺序，等待数据fetch之后再执行相应的操作
    private static void testThreadStop() {
        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        try {
            // Waits for this thread to die. 调用join会阻塞主线程
            thread.join();
        } catch (InterruptedException exception) {
            System.out.println("Interrupted");
        }
    }
}
