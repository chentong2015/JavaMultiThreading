// 一个进程中包含多个线程，通过进程ID来定位
public class BaseThreadId {

    public static void main(String[] args) throws InterruptedException {
        long pid = ProcessHandle.current().pid();
        System.out.println(pid);

        long tid = Thread.currentThread().getId(); // main thread
        System.out.println("Thread ID: " + tid);

        new Thread(() -> {
            System.out.println("run new thread");
            System.out.println(Thread.currentThread().getId()); // new thread
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        Thread.sleep(300000);

        // 获取所有线程的ID
        Thread.getAllStackTraces().keySet().forEach(t -> {
            System.out.println(t.getName() + " : " + t.getId());
        });
    }
}