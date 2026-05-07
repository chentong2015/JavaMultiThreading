package thread_interrupt;

// TODO. 中断线程多次时，必须判断处于中断状态的什么阶段
public class ThreadInterruptedDouble {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Inside: " + Thread.currentThread().isInterrupted());
                try {
                    System.out.println("Run sleeping");
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    System.out.println("Second interrupt caught");
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);

        thread.interrupt();
        Thread.sleep(1000);
        System.out.println("Outer 1: " + thread.isInterrupted()); // false

        thread.interrupt();
        Thread.sleep(1000);
        System.out.println("Outer 2: " + thread.isInterrupted()); // true

        Thread.sleep(3000);
        System.out.println("Outer 3: " + thread.isInterrupted()); // true
    }
}
