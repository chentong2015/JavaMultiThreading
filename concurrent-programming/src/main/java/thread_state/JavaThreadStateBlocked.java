package thread_state;

public class JavaThreadStateBlocked {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread A get lock and run ...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread A release lock");
            }
        });
        threadA.start();

        Thread threadB = new Thread(() -> {
            System.out.println("Thread B try to get lock ...");
            synchronized (lock) {
                System.out.println("Thread B get lock ");
            }
        });
        Thread.sleep(1000); // Make sure A gets lock
        threadB.start();
        Thread.sleep(1000); // Make sure B goes to BLOCKED

        System.out.println("Thread A State: " + threadA.getState());
        System.out.println("Thread B State: " + threadB.getState());
    }
}
