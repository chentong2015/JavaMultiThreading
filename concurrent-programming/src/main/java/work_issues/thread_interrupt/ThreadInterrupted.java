package work_issues.thread_interrupt;

// TODO. 线程被中断的结果:
// 清除中断标识并抛出异常InterruptedException
// Its interrupt status will be cleared and it will receive an InterruptedException
public class ThreadInterrupted {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO. 线程被中断后一定会抛出异常，在这里被捕获
                System.out.println(e.getMessage());
            }
        });
        thread.start();

        Thread.sleep(2000);

        // 立即中断，瞬间时间获取的状态为ture，表示未被清除的状态
        // thread.interrupt();
        // System.out.println(thread.isInterrupted());

        // 立即中断，延迟获取状态为清除后的false
        thread.interrupt();
        Thread.sleep(500);

        // TODO. 为什么thread线程被中断后，拿到的标志却为false
        System.out.println(thread.isInterrupted());

        // 外层拿到false标志可能造成后续的问题
        // 即使外层瞬时拿到true标志也会由于catch清除而变成false
        while (!thread.isInterrupted()) {
            // Continue to run if the thread is not interrupted !!
        }
        System.out.println("All done");
    }
}
